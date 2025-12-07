package com.example.lol.service;

import com.example.lol.model.Match;

import java.util.List;

/**
 * Service interface for Match-related operations.
 */
public interface MatchService {

    /**
     * Gets match history for a player.
     * 
     * @param gameName Player's game name (Riot ID)
     * @param tagLine  Player's tag line
     * @return List of recent matches
     */
    List<Match> getMatches(String gameName, String tagLine);
}
