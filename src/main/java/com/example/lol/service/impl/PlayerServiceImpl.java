package com.example.lol.service.impl;

import com.example.lol.helper.RiotApiHelper;
import com.example.lol.model.PlayerInfo;
import com.example.lol.model.RiotAccount;
import com.example.lol.model.Summoner;
import com.example.lol.service.DataDragonService;
import com.example.lol.service.PlayerService;
import org.springframework.stereotype.Service;

/**
 * Implementation of PlayerService.
 * Handles player/account data operations from Riot API.
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    private final RiotApiHelper riotApiHelper;
    private final DataDragonService dataDragonService;

    public PlayerServiceImpl(RiotApiHelper riotApiHelper, DataDragonService dataDragonService) {
        this.riotApiHelper = riotApiHelper;
        this.dataDragonService = dataDragonService;
    }

    @Override
    public RiotAccount getAccountByRiotId(String gameName, String tagLine) {
        try {
            String url = riotApiHelper.buildAccountByRiotIdUrl(gameName, tagLine);
            return riotApiHelper.executeGet(url, RiotAccount.class);
        } catch (Exception e) {
            logError("fetching account", e);
            return null;
        }
    }

    @Override
    public Summoner getSummonerByPuuid(String puuid) {
        try {
            String url = riotApiHelper.buildSummonerByPuuidUrl(puuid);
            return riotApiHelper.executeGet(url, Summoner.class);
        } catch (Exception e) {
            logError("fetching summoner", e);
            return null;
        }
    }

    @Override
    public PlayerInfo getPlayerInfo(String gameName, String tagLine) {
        try {
            // Step 1: Get account data
            RiotAccount account = getAccountByRiotId(gameName, tagLine);
            if (account == null) {
                return null;
            }

            // Step 2: Get summoner data
            Summoner summoner = getSummonerByPuuid(account.getPuuid());
            if (summoner == null) {
                return null;
            }

            // Step 3: Build player info
            String version = dataDragonService.getLatestVersion();
            String profileIconUrl = riotApiHelper.buildProfileIconUrl(version, summoner.getProfileIconId());

            return new PlayerInfo(
                    account.getGameName(),
                    account.getTagLine(),
                    account.getPuuid(),
                    null, // summonerId - not available in current Riot API
                    summoner.getProfileIconId(),
                    summoner.getSummonerLevel(),
                    profileIconUrl);
        } catch (Exception e) {
            logError("fetching player info", e);
            return null;
        }
    }

    private void logError(String operation, Exception e) {
        System.err.println("Error " + operation + ": " + e.getMessage());
    }
}
