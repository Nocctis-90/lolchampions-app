package com.example.lol.service;

import com.example.lol.model.Champion;
import com.example.lol.model.ChampionDetail;
import com.example.lol.model.ChampionRotation;

import java.util.List;

/**
 * Service interface for Champion-related operations.
 */
public interface ChampionService {

    /**
     * Gets all champions from Data Dragon.
     * 
     * @return List of all champions
     */
    List<Champion> getAllChampions();

    /**
     * Gets detailed information about a specific champion.
     * 
     * @param championId The champion ID
     * @return ChampionDetail or null if not found
     */
    ChampionDetail getChampionDetail(String championId);

    /**
     * Gets the current free champion rotation from Riot API.
     * 
     * @return ChampionRotation data or null on error
     */
    ChampionRotation getChampionRotation();

    /**
     * Gets the list of free champions with full details.
     * 
     * @return List of free rotation champions
     */
    List<Champion> getFreeChampions();
}
