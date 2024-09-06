package com.twitchplus.backend.external.model;

import java.util.List;

public record ClipResponse(
        List<Clip> data
) {
}
