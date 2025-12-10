package com.example.lol.service.impl;

import com.example.lol.model.RankedInfo;
import com.example.lol.service.RankedService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RankedServiceImpl implements RankedService {

    @Value("${riot.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String RANKED_URL = "https://br1.api.riotgames.com/lol/league/v4/entries/by-puuid/";

    @Override
    public List<RankedInfo> getRankedInfo(String puuid) {
        String url = RANKED_URL + puuid + "?api_key=" + apiKey;

        try {
            RankedInfo[] rankedArray = restTemplate.getForObject(url, RankedInfo[].class);

            if (rankedArray != null && rankedArray.length > 0) {
                System.out.println("Ranked data found: " + rankedArray.length + " entries");
                for (RankedInfo info : rankedArray) {
                    System.out.println("  - " + info.getQueueType() + ": " + info.getTier() + " " + info.getRank());
                }
            } else {
                System.out.println("No ranked data found for this player");
            }

            return rankedArray != null ? Arrays.asList(rankedArray) : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error fetching ranked info: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
