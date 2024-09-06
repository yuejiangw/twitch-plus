package com.twitchplus.backend.external.model;

import java.util.List;

public record GameResponse(
        List<Game> data
) {
}
