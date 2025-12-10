package com.example.lol.service;

import com.example.lol.model.RankedInfo;

import java.util.List;

public interface RankedService {
    /**
     * Get current ranked information for a summoner by their PUUID
     * Uses Riot API - returns real data
     */
    List<RankedInfo> getRankedInfo(String puuid);
}
