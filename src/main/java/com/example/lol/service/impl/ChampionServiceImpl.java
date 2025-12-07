package com.example.lol.service.impl;

import com.example.lol.helper.ChampionHelper;
import com.example.lol.helper.RiotApiHelper;
import com.example.lol.model.*;
import com.example.lol.service.ChampionService;
import com.example.lol.service.DataDragonService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ChampionService.
 * Handles champion data operations from Data Dragon and Riot API.
 */
@Service
public class ChampionServiceImpl implements ChampionService {

    private final RestTemplate restTemplate;
    private final DataDragonService dataDragonService;
    private final RiotApiHelper riotApiHelper;
    private final ChampionHelper championHelper;

    private static final String DDRAGON_BASE = "https://ddragon.leagueoflegends.com/cdn";
    private static final String CHAMPION_PATH = "/data/pt_BR/champion.json";
    private static final String CHAMPION_DETAIL_PATH = "/data/pt_BR/champion/%s.json";

    public ChampionServiceImpl(RestTemplate restTemplate,
            DataDragonService dataDragonService,
            RiotApiHelper riotApiHelper,
            ChampionHelper championHelper) {
        this.restTemplate = restTemplate;
        this.dataDragonService = dataDragonService;
        this.riotApiHelper = riotApiHelper;
        this.championHelper = championHelper;
    }

    @Override
    public List<Champion> getAllChampions() {
        try {
            String version = dataDragonService.getLatestVersion();
            String url = DDRAGON_BASE + "/" + version + CHAMPION_PATH;
            ChampionResponse response = restTemplate.getForObject(url, ChampionResponse.class);

            if (response != null && response.getData() != null) {
                return new ArrayList<>(response.getData().values());
            }
        } catch (Exception e) {
            logError("fetching champions", e);
        }
        return new ArrayList<>();
    }

    @Override
    public ChampionDetail getChampionDetail(String championId) {
        try {
            String version = dataDragonService.getLatestVersion();
            String url = DDRAGON_BASE + "/" + version + String.format(CHAMPION_DETAIL_PATH, championId);
            ChampionDetailResponse response = restTemplate.getForObject(url, ChampionDetailResponse.class);

            if (response != null && response.getData() != null) {
                return response.getData().get(championId);
            }
        } catch (Exception e) {
            logError("fetching champion detail for " + championId, e);
        }
        return null;
    }

    @Override
    public ChampionRotation getChampionRotation() {
        try {
            String url = riotApiHelper.buildChampionRotationUrl();
            return riotApiHelper.executeGet(url, ChampionRotation.class);
        } catch (Exception e) {
            logError("fetching champion rotation", e);
            return null;
        }
    }

    @Override
    public List<Champion> getFreeChampions() {
        try {
            ChampionRotation rotation = getChampionRotation();
            List<Champion> allChampions = getAllChampions();
            return championHelper.filterByRotation(allChampions, rotation);
        } catch (Exception e) {
            logError("fetching free champions", e);
            return new ArrayList<>();
        }
    }

    private void logError(String operation, Exception e) {
        System.err.println("Error " + operation + ": " + e.getMessage());
    }
}
