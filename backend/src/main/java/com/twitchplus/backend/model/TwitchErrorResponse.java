package com.twitchplus.backend.model;

public record TwitchErrorResponse(
        String message,
        String error,
        String details
) {
}
