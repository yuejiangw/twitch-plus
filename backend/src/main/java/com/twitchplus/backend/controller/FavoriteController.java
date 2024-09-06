package com.twitchplus.backend.controller;

import com.twitchplus.backend.dao.entity.UserEntity;
import com.twitchplus.backend.exception.DuplicateFavoriteException;
import com.twitchplus.backend.model.FavoriteRequestBody;
import com.twitchplus.backend.model.TypeGroupedItemList;
import com.twitchplus.backend.service.FavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    private final FavoriteService favoriteService;

    // Hard-coded user for temporary use, will be replaced in future
    private final UserEntity userEntity = new UserEntity(1L, "user0", "Foo", "Bar", "password");

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public TypeGroupedItemList getFavoriteItems() {
        return favoriteService.getGroupedFavoriteItems(userEntity);
    }

    @PostMapping
    public void setFavoriteItem(@RequestBody FavoriteRequestBody body) {
        try {
            favoriteService.setFavoriteItem(userEntity, body.favorite());
        } catch (DuplicateFavoriteException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate entry for favorite record", e);
        }
    }

    @DeleteMapping
    public void unsetFavoriteItem(@RequestBody FavoriteRequestBody body) {
        favoriteService.unsetFavoriteItem(userEntity, body.favorite().twitchId());
    }
}
