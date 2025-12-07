package com.example.lol.service;

import com.example.lol.model.Champion;
import com.example.lol.model.ChampionResponse;
import com.example.lol.model.ChampionDetail;
import com.example.lol.model.ChampionDetailResponse;
import com.example.lol.model.Item;
import com.example.lol.model.ItemResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LolService {

    private final RestTemplate restTemplate;
    private static final String VERSION_URL = "https://ddragon.leagueoflegends.com/api/versions.json";
    private static final String CHAMPION_URL = "https://ddragon.leagueoflegends.com/cdn/%s/data/en_US/champion.json";

    @org.springframework.beans.factory.annotation.Value("${riot.api.key}")
    private String riotApiKey;

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

    public java.util.List<Item> getItems() {
        String version = getLatestVersion();
        String url = String.format("https://ddragon.leagueoflegends.com/cdn/%s/data/en_US/item.json", version);
        ItemResponse response = restTemplate.getForObject(url, ItemResponse.class);

        if (response != null && response.getData() != null) {
            return new ArrayList<>(response.getData().values());
        }
        return new ArrayList<>();
    }

    public List<com.example.lol.model.Match> getMatches(String gameName, String tagLine) {
        try {
            // Step 1: Get PUUID from Riot ID (GameName + TagLine)
            String accountUrl = String.format(
                    "https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%s/%s",
                    gameName, tagLine);

            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("X-Riot-Token", riotApiKey);
            org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);

            org.springframework.http.ResponseEntity<com.example.lol.model.RiotAccount> accountResponse = restTemplate
                    .exchange(accountUrl, org.springframework.http.HttpMethod.GET, entity,
                            com.example.lol.model.RiotAccount.class);

            String puuid = accountResponse.getBody().getPuuid();

            // Step 2: Get Match IDs by PUUID (last 10 matches)
            String matchIdsUrl = String.format(
                    "https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/%s/ids?start=0&count=10",
                    puuid);

            org.springframework.http.ResponseEntity<String[]> matchIdsResponse = restTemplate.exchange(matchIdsUrl,
                    org.springframework.http.HttpMethod.GET, entity, String[].class);

            String[] matchIds = matchIdsResponse.getBody();
            List<com.example.lol.model.Match> matches = new ArrayList<>();

            // Step 3: Get details for each match
            if (matchIds != null) {
                for (String matchId : matchIds) {
                    String matchDetailUrl = String.format(
                            "https://americas.api.riotgames.com/lol/match/v5/matches/%s",
                            matchId);

                    org.springframework.http.ResponseEntity<com.example.lol.model.MatchDetail> matchResponse = restTemplate
                            .exchange(matchDetailUrl, org.springframework.http.HttpMethod.GET, entity,
                                    com.example.lol.model.MatchDetail.class);

                    com.example.lol.model.MatchDetail matchDetail = matchResponse.getBody();
                    if (matchDetail != null && matchDetail.getInfo() != null) {
                        // Find the participant data for this player
                        for (com.example.lol.model.MatchParticipant participant : matchDetail.getInfo()
                                .getParticipants()) {
                            if (participant.getPuuid().equals(puuid)) {
                                String kda = String.format("%d/%d/%d",
                                        participant.getKills(),
                                        participant.getDeaths(),
                                        participant.getAssists());

                                String gameDate = new java.text.SimpleDateFormat("yyyy-MM-dd")
                                        .format(new java.util.Date(matchDetail.getInfo().getGameCreation()));

                                matches.add(new com.example.lol.model.Match(
                                        matchId,
                                        participant.getChampionName(),
                                        participant.isWin(),
                                        matchDetail.getInfo().getGameMode(),
                                        kda,
                                        gameDate));
                                break;
                            }
                        }
                    }
                }
            }

            return matches;

        } catch (Exception e) {
            System.err.println("Error fetching matches: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public com.example.lol.model.PlayerInfo getPlayerInfo(String gameName, String tagLine) {
        try {
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("X-Riot-Token", riotApiKey);
            org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);

            // Step 1: Get PUUID from Riot ID (GameName + TagLine) using Account-V1
            String accountUrl = String.format(
                    "https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%s/%s",
                    gameName, tagLine);

            org.springframework.http.ResponseEntity<com.example.lol.model.RiotAccount> accountResponse = restTemplate
                    .exchange(accountUrl, org.springframework.http.HttpMethod.GET, entity,
                            com.example.lol.model.RiotAccount.class);

            com.example.lol.model.RiotAccount account = accountResponse.getBody();
            String puuid = account.getPuuid();

            // Step 2: Get Summoner data by PUUID using Summoner-V4
            // Note: You need to use the correct regional endpoint (br1, na1, euw1, etc.)
            String summonerUrl = String.format(
                    "https://br1.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/%s",
                    puuid);

            org.springframework.http.ResponseEntity<com.example.lol.model.Summoner> summonerResponse = restTemplate
                    .exchange(summonerUrl, org.springframework.http.HttpMethod.GET, entity,
                            com.example.lol.model.Summoner.class);

            com.example.lol.model.Summoner summoner = summonerResponse.getBody();

            // Build profile icon URL
            String version = getLatestVersion();
            String profileIconUrl = String.format(
                    "https://ddragon.leagueoflegends.com/cdn/%s/img/profileicon/%d.png",
                    version, summoner.getProfileIconId());

            return new com.example.lol.model.PlayerInfo(
                    account.getGameName(),
                    account.getTagLine(),
                    puuid,
                    summoner.getProfileIconId(),
                    summoner.getSummonerLevel(),
                    profileIconUrl);

        } catch (Exception e) {
            System.err.println("Error fetching player info: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
