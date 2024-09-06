package com.twitchplus.backend.external.model;

import java.util.List;

public record VideoResponse(
        List<Video> data
) {
}
