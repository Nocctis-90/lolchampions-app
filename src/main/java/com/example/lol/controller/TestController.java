package com.example.lol.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "http://localhost:4200")
public class TestController {

    @Value("${riot.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/ranked/{summonerId}")
    public Map<String, Object> testRankedApi(@PathVariable String summonerId) {
        Map<String, Object> response = new HashMap<>();

        String url = "https://br1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerId + "?api_key="
                + apiKey;

        response.put("summonerId", summonerId);
        response.put("url", "https://br1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerId);

        try {
            String rawResponse = restTemplate.getForObject(url, String.class);
            response.put("success", true);
            response.put("rawResponse", rawResponse);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            response.put("errorClass", e.getClass().getName());
        }

        return response;
    }

    @GetMapping("/summoner/{gameName}/{tagLine}")
    public Map<String, Object> testSummonerLookup(@PathVariable String gameName, @PathVariable String tagLine) {
        Map<String, Object> response = new HashMap<>();

        // First get account
        String accountUrl = "https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + gameName + "/"
                + tagLine + "?api_key=" + apiKey;

        try {
            String accountResponse = restTemplate.getForObject(accountUrl, String.class);
            response.put("accountSuccess", true);
            response.put("accountResponse", accountResponse);

            // Try to extract PUUID and get summoner
            // This is just for debugging
        } catch (Exception e) {
            response.put("accountSuccess", false);
            response.put("accountError", e.getMessage());
        }

        return response;
    }
}
