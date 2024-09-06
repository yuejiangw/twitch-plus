package com.twitchplus.backend.model;

import com.twitchplus.backend.dao.entity.ItemEntity;

public record FavoriteRequestBody(
        ItemEntity favorite
) {
}
