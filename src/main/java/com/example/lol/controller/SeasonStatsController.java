package com.example.lol.controller;

import com.example.lol.model.SeasonStats;
import com.example.lol.service.SeasonStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/season")
@CrossOrigin(origins = "http://localhost:4200")
public class SeasonStatsController {

    @Autowired
    private SeasonStatsService seasonStatsService;

    @GetMapping("/{puuid}/{season}")
    public SeasonStats getSeasonStats(@PathVariable String puuid, @PathVariable int season) {
        return seasonStatsService.getSeasonStats(puuid, season);
    }
}
