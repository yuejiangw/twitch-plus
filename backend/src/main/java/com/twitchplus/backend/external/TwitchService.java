package com.twitchplus.backend.external;

import com.twitchplus.backend.external.model.Clip;
import com.twitchplus.backend.external.model.Game;
import com.twitchplus.backend.external.model.Stream;
import com.twitchplus.backend.external.model.Video;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TwitchService {
    private final TwitchApiClient twitchApiClient;

    public TwitchService(final TwitchApiClient twitchApiClient) {
        this.twitchApiClient = twitchApiClient;
    }

    @Cacheable("top_games")
    public List<Game> getTopGames() {
        return twitchApiClient.getTopGames().data();
    }

    @Cacheable("games_by_name")
    public List<Game> getGames(String name) {
        return twitchApiClient.getGames(name).data();
    }

    public List<Stream> getStreams(List<String> gameIds, int first) {
        return twitchApiClient.getStreams(gameIds, first).data();
    }

    public List<Video> getVideos(String gameId, int first) {
        return twitchApiClient.getVideos(gameId, first).data();
    }

    public List<Clip> getClips(String gameId, int first) {
        return twitchApiClient.getClips(gameId, first).data();
    }

    public List<String> getTopGameIds() {
        List<String> topGameIds = new ArrayList<>();
        for (Game game : getTopGames()) {
            topGameIds.add(game.id());
        }
        return topGameIds;
    }
}
