package com.twitchplus.backend.external.model;

import java.util.List;

public record StreamResponse(
        List<Stream> data
) {
}
