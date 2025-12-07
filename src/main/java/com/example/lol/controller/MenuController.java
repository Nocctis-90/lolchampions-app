package com.example.lol.controller;

import com.example.lol.model.Item;
import com.example.lol.service.LolService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class MenuController {

    private final LolService lolService;

    public MenuController(LolService lolService) {
        this.lolService = lolService;
    }

    @GetMapping
    public List<Item> getItems() {
        return lolService.getItems();
    }
}
