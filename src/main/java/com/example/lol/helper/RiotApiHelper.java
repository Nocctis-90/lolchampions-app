package com.example.lol.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Helper class for Riot API interactions.
 * Centralizes API configuration, headers, and base URLs.
 */
@Component
public class RiotApiHelper {

    private final RestTemplate restTemplate;

    @Value("${riot.api.key}")
    private String riotApiKey;

    // Regional endpoints
    public static final String AMERICAS_BASE_URL = "https://americas.api.riotgames.com";
    public static final String BR1_BASE_URL = "https://br1.api.riotgames.com";
    public static final String DDRAGON_BASE_URL = "https://ddragon.leagueoflegends.com";

    // API Paths
    public static final String ACCOUNT_BY_RIOT_ID = "/riot/account/v1/accounts/by-riot-id/%s/%s";
    public static final String SUMMONER_BY_PUUID = "/lol/summoner/v4/summoners/by-puuid/%s";
    public static final String MATCHES_BY_PUUID = "/lol/match/v5/matches/by-puuid/%s/ids";
    public static final String MATCH_BY_ID = "/lol/match/v5/matches/%s";
    public static final String CHAMPION_ROTATION = "/lol/platform/v3/champion-rotations";

    public RiotApiHelper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Creates HTTP headers with Riot API token.
     */
    public HttpHeaders createAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", riotApiKey);
        return headers;
    }

    /**
     * Creates HTTP entity with auth headers.
     */
    public HttpEntity<String> createAuthEntity() {
        return new HttpEntity<>(createAuthHeaders());
    }

    /**
     * Executes a GET request to Riot API with authentication.
     */
    public <T> T executeGet(String url, Class<T> responseType) {
        HttpEntity<String> entity = createAuthEntity();
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        return response.getBody();
    }

    /**
     * Builds URL for Account API - Get by Riot ID.
     */
    public String buildAccountByRiotIdUrl(String gameName, String tagLine) {
        return AMERICAS_BASE_URL + String.format(ACCOUNT_BY_RIOT_ID, gameName, tagLine);
    }

    /**
     * Builds URL for Summoner API - Get by PUUID.
     */
    public String buildSummonerByPuuidUrl(String puuid) {
        return BR1_BASE_URL + String.format(SUMMONER_BY_PUUID, puuid);
    }

    /**
     * Builds URL for Match API - Get match IDs by PUUID.
     */
    public String buildMatchesByPuuidUrl(String puuid, int start, int count) {
        return AMERICAS_BASE_URL + String.format(MATCHES_BY_PUUID, puuid) + "?start=" + start + "&count=" + count;
    }

    /**
     * Builds URL for Match API - Get match details by ID.
     */
    public String buildMatchByIdUrl(String matchId) {
        return AMERICAS_BASE_URL + String.format(MATCH_BY_ID, matchId);
    }

    /**
     * Builds URL for Platform API - Champion rotation.
     */
    public String buildChampionRotationUrl() {
        return BR1_BASE_URL + CHAMPION_ROTATION;
    }

    /**
     * Builds URL for Data Dragon - Profile icon.
     */
    public String buildProfileIconUrl(String version, int iconId) {
        return String.format("%s/cdn/%s/img/profileicon/%d.png", DDRAGON_BASE_URL, version, iconId);
    }

    /**
     * Builds URL for Data Dragon - Champion image.
     */
    public String buildChampionImageUrl(String version, String championImageFull) {
        return String.format("%s/cdn/%s/img/champion/%s", DDRAGON_BASE_URL, version, championImageFull);
    }
}
