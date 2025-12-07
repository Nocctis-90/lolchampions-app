package com.example.lol.service;

import com.example.lol.model.Item;

import java.util.List;

/**
 * Service interface for Item-related operations.
 */
public interface ItemService {

    /**
     * Gets all items from Data Dragon.
     * 
     * @return List of all items
     */
    List<Item> getAllItems();
}
