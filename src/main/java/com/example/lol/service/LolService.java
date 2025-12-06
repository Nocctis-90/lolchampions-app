package com.example.lol.service;

import com.example.lol.model.Champion;
import com.example.lol.model.ChampionResponse;
import com.example.lol.model.ChampionDetail;
import com.example.lol.model.ChampionDetailResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LolService {

    private final RestTemplate restTemplate;
    private static final String VERSION_URL = "https://ddragon.leagueoflegends.com/api/versions.json";
    private static final String CHAMPION_URL = "https://ddragon.leagueoflegends.com/cdn/%s/data/en_US/champion.json";

    public LolService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getLatestVersion() {
        String[] versions = restTemplate.getForObject(VERSION_URL, String[].class);
        if (versions != null && versions.length > 0) {
            return versions[0];
        }
        return "13.24.1"; // Fallback version
    }

    public List<Champion> getChampions() {
        String version = getLatestVersion();
        String url = String.format(CHAMPION_URL, version);
        ChampionResponse response = restTemplate.getForObject(url, ChampionResponse.class);

        if (response != null && response.getData() != null) {
            return new ArrayList<>(response.getData().values());
        }
        return new ArrayList<>();
    }

    public String getVersion() {
        return getLatestVersion();
    }

    public ChampionDetail getChampionDetail(String championId) {
        String version = getLatestVersion();
        String url = String.format("https://ddragon.leagueoflegends.com/cdn/%s/data/en_US/champion/%s.json", version,
                championId);
        ChampionDetailResponse response = restTemplate.getForObject(url, ChampionDetailResponse.class);

        if (response != null && response.getData() != null) {
            return response.getData().get(championId);
        }
        return null;
    }
}
