package com.example.lol.service;

import com.example.lol.model.SeasonStats;

public interface SeasonStatsService {
    /**
     * Get detailed season statistics for a player
     * Includes champion breakdown, multi-kills, and averages
     */
    SeasonStats getSeasonStats(String puuid, int season);
}
