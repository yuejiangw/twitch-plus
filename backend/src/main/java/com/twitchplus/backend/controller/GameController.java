package com.twitchplus.backend.controller;

import com.twitchplus.backend.service.TwitchService;
import com.twitchplus.backend.external.model.Game;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {
    private final TwitchService twitchService;

    public GameController(final TwitchService twitchService) {
        this.twitchService = twitchService;
    }

    @GetMapping("/game")
    public List<Game> getGames(@RequestParam(value = "game_name", required = false) String gameName) {
        if (gameName == null) {
            return twitchService.getTopGames();
        } else {
            return twitchService.getGames(gameName);
        }
    }
}
