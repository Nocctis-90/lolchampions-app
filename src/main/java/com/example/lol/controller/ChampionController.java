package com.example.lol.controller;

import com.example.lol.model.Champion;
import com.example.lol.model.ChampionDetail;
import com.example.lol.service.LolService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/api/champions")
public class ChampionController {

    private final LolService lolService;

    public ChampionController(LolService lolService) {
        this.lolService = lolService;
    }

    @GetMapping
    public List<Champion> getChampions() {
        return lolService.getChampions();
    }

    @GetMapping("/{id}")
    public ChampionDetail getChampionDetail(@PathVariable String id) {
        return lolService.getChampionDetail(id);
    }
}
