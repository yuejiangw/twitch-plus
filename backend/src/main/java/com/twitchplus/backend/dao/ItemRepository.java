package com.twitchplus.backend.dao;

import com.twitchplus.backend.dao.entity.ItemEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface ItemRepository extends ListCrudRepository<ItemEntity, Long> {
    ItemEntity findByTwitchId(String twitchId);
}
