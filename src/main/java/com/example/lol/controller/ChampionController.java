package com.example.lol.controller;

import com.example.lol.model.Champion;
import com.example.lol.model.ChampionDetail;
import com.example.lol.model.ChampionRotation;
import com.example.lol.service.ChampionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Champion-related endpoints.
 */
@RestController
@RequestMapping("/api/champions")
@CrossOrigin(origins = "*")
public class ChampionController {

    private final ChampionService championService;

    public ChampionController(ChampionService championService) {
        this.championService = championService;
    }

    /**
     * Get all champions.
     */
    @GetMapping
    public List<Champion> getChampions() {
        return championService.getAllChampions();
    }

    /**
     * Get champion details by ID.
     */
    @GetMapping("/{id}")
    public ChampionDetail getChampionDetail(@PathVariable String id) {
        return championService.getChampionDetail(id);
    }

    /**
     * Get free champion rotation with full details.
     */
    @GetMapping("/rotation")
    public List<Champion> getFreeRotation() {
        return championService.getFreeChampions();
    }

    /**
     * Get raw rotation data (champion IDs only).
     */
    @GetMapping("/rotation/raw")
    public ChampionRotation getRotationRaw() {
        return championService.getChampionRotation();
    }
}
