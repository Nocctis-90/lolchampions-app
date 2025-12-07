package com.example.lol.controller;

import com.example.lol.model.Item;
import com.example.lol.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Item and Menu-related endpoints.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MenuController {

    private final ItemService itemService;

    public MenuController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Get all game items.
     */
    @GetMapping("/items")
    public List<Item> getItems() {
        return itemService.getAllItems();
    }
}
