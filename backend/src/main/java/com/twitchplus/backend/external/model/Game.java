package com.twitchplus.backend.external.model;

import com.fasterxml.jackson.annotation.JsonProperty;

// ref: https://dev.twitch.tv/docs/api/reference/#get-games
public record Game(
        String id,
        String name,
        @JsonProperty("box_art_url") String boxArtUrl
) {
}
