package com.example.lol.service;

import com.example.lol.model.PlayerInfo;
import com.example.lol.model.RiotAccount;
import com.example.lol.model.Summoner;

/**
 * Service interface for Player/Account-related operations.
 */
public interface PlayerService {

    /**
     * Gets account information by Riot ID.
     * 
     * @param gameName Player's game name
     * @param tagLine  Player's tag line
     * @return RiotAccount or null if not found
     */
    RiotAccount getAccountByRiotId(String gameName, String tagLine);

    /**
     * Gets summoner information by PUUID.
     * 
     * @param puuid Player's PUUID
     * @return Summoner or null if not found
     */
    Summoner getSummonerByPuuid(String puuid);

    /**
     * Gets combined player information (account + summoner).
     * 
     * @param gameName Player's game name
     * @param tagLine  Player's tag line
     * @return PlayerInfo or null if not found
     */
    PlayerInfo getPlayerInfo(String gameName, String tagLine);
}
