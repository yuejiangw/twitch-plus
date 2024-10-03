package com.twitchplus.backend.external;

import com.twitchplus.backend.external.model.ClipResponse;
import com.twitchplus.backend.external.model.GameResponse;
import com.twitchplus.backend.external.model.StreamResponse;
import com.twitchplus.backend.external.model.VideoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "twitch-api")
public interface TwitchApiClient {
    @GetMapping("/games")
    GameResponse getGames(@RequestParam("name") String name);

    @GetMapping("/games/top")
    GameResponse getTopGames(); // ref: https://dev.twitch.tv/docs/api/reference/#get-top-games

    @GetMapping("/videos/")
    VideoResponse getVideos(@RequestParam("game_id") String gameId, @RequestParam("first") int first);

    @GetMapping("/clips/")
    ClipResponse getClips(@RequestParam("game_id") String gameId, @RequestParam("first") int first);

    @GetMapping("/streams/")
    StreamResponse getStreams(@RequestParam("game_id") List<String> gameIds, @RequestParam("first") int first);
}
