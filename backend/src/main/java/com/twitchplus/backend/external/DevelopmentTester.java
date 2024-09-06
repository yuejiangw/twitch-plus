package com.twitchplus.backend.external;

import com.twitchplus.backend.external.model.Clip;
import com.twitchplus.backend.external.model.Game;
import com.twitchplus.backend.external.model.Stream;
import com.twitchplus.backend.external.model.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DevelopmentTester implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(DevelopmentTester.class);

    private final TwitchService twitchService;

    public DevelopmentTester(final TwitchService twitchService) {
        this.twitchService = twitchService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Game> games = twitchService.getGames("fortnite");
        List<Game> topGames = twitchService.getTopGames();
        List<Video> videos = twitchService.getVideos("21779", 2);
        List<Clip> clips = twitchService.getClips("21779", 2);
        List<Stream> streams = twitchService.getStreams(List.of("18122", "21779", "33214"), 10);

        logger.info(games.toString());
        logger.info(topGames.toString());
        logger.info(videos.toString());
        logger.info(clips.toString());
        logger.info(streams.toString());
    }
}
