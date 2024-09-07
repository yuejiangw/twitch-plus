package com.twitchplus.backend.service;

import com.twitchplus.backend.dao.entity.ItemEntity;
import com.twitchplus.backend.dao.entity.UserEntity;
import com.twitchplus.backend.external.TwitchService;
import com.twitchplus.backend.external.model.Clip;
import com.twitchplus.backend.external.model.Stream;
import com.twitchplus.backend.external.model.Video;
import com.twitchplus.backend.model.TypeGroupedItemList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecommendationService {
    private static final int MAX_GAME_SEED = 3;
    private static final int PER_PAGE_ITEM_SIZE = 20;

    private final TwitchService twitchService;

    private final FavoriteService favoriteService;

    public RecommendationService(TwitchService twitchService, FavoriteService favoriteService) {
        this.twitchService = twitchService;
        this.favoriteService = favoriteService;
    }

    public TypeGroupedItemList recommendItems(UserEntity userEntity) {
        List<String> gameIds;
        Set<String> exclusions = new HashSet<>();

        // 如果用户为空（未登录），获取热门游戏 ID
        if (userEntity == null) {
            gameIds = twitchService.getTopGameIds();
        } else {
            // 获取用户收藏的项目
            List<ItemEntity> items = favoriteService.getFavoriteItems(userEntity);

            if (items.isEmpty()) {
                // 如果没有收藏项目，获取热门游戏 ID
                gameIds = twitchService.getTopGameIds();
            } else {
                // 否则，从用户收藏项目中提取唯一的游戏 ID，并将收藏的项目 ID 添加到排除列表
                Set<String> uniqueGameIds = new HashSet<>();
                for (ItemEntity item : items) {
                    uniqueGameIds.add(item.gameId());
                    exclusions.add(item.twitchId());
                }
                gameIds = new ArrayList<>(uniqueGameIds);
            }
        }

        // 限制游戏 ID 列表的大小为 MAX_GAME_SEED，防止一次请求过多游戏
        int gameSize = Math.min(gameIds.size(), MAX_GAME_SEED);

        // 计算每个游戏推荐的项目数量
        int perGameListSize = PER_PAGE_ITEM_SIZE / gameSize;

        // 根据游戏 ID 和排除列表推荐直播流
        List<ItemEntity> streams = recommendStreams(gameIds, exclusions);

        // 根据前 gameSize 个游戏推荐剪辑和视频
        List<ItemEntity> clips = recommendClips(gameIds.subList(0, gameSize), perGameListSize, exclusions);
        List<ItemEntity> videos = recommendVideos(gameIds.subList(0, gameSize), perGameListSize, exclusions);

        // 将推荐的流、视频、剪辑分类打包为 TypeGroupedItemList 返回
        return new TypeGroupedItemList(streams, videos, clips);
    }

    private List<ItemEntity> recommendStreams(List<String> gameIds, Set<String> exclusions) {
        // 调用 Twitch 服务，根据游戏 ID 获取直播流列表，每页最多 PER_PAGE_ITEM_SIZE 个流
        List<Stream> streams = twitchService.getStreams(gameIds, PER_PAGE_ITEM_SIZE);

        // 创建一个新的列表用于存放结果项
        List<ItemEntity> resultItems = new ArrayList<>();

        // 遍历所有获取到的直播流
        for (Stream stream : streams) {
            // 如果当前直播流的 ID 不在排除集合 exclusions 中
            if (!exclusions.contains(stream.id())) {
                // 将这个直播流包装成 ItemEntity 并添加到结果列表中
                resultItems.add(new ItemEntity(stream));
            }
        }

        // 返回最终推荐的直播流项目列表
        return resultItems;
    }


    private List<ItemEntity> recommendVideos(List<String> gameIds, int perGameListSize, Set<String> exclusions) {
        List<ItemEntity> resultItems = new ArrayList<>();
        for (String gameId : gameIds) {
            List<Video> listPerGame = twitchService.getVideos(gameId, perGameListSize);
            for (Video video : listPerGame) {
                if (!exclusions.contains(video.id())) {
                    resultItems.add(new ItemEntity(gameId, video));
                }
            }
        }
        return resultItems;
    }

    private List<ItemEntity> recommendClips(List<String> gameIds, int perGameListSize, Set<String> exclusions) {
        List<ItemEntity> resultItem = new ArrayList<>();
        for (String gameId : gameIds) {
            List<Clip> listPerGame = twitchService.getClips(gameId, perGameListSize);
            for (Clip clip : listPerGame) {
                if (!exclusions.contains(clip.id())) {
                    resultItem.add(new ItemEntity(clip));
                }
            }
        }
        return resultItem;
    }
}
