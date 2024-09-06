package com.twitchplus.backend.controller;

import com.twitchplus.backend.model.TypeGroupedItemList;
import com.twitchplus.backend.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/search")
    public TypeGroupedItemList search(@RequestParam("game_id") String gameId) {
        return itemService.getItems(gameId);
    }
}
