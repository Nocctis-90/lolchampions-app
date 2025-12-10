package com.example.lol.service.impl;

import com.example.lol.model.ChampionSeasonStats;
import com.example.lol.model.SeasonStats;
import com.example.lol.service.DataDragonService;
import com.example.lol.service.RankedService;
import com.example.lol.service.SeasonStatsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SeasonStatsServiceImpl implements SeasonStatsService {

    @Value("${riot.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final RankedService rankedService;
    private final DataDragonService dataDragonService;

    private static final String MATCH_IDS_URL = "https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/";
    private static final String MATCH_DETAIL_URL = "https://americas.api.riotgames.com/lol/match/v5/matches/";

    public SeasonStatsServiceImpl(RankedService rankedService, DataDragonService dataDragonService) {
        this.rankedService = rankedService;
        this.dataDragonService = dataDragonService;
    }

    @Override
    public SeasonStats getSeasonStats(String puuid, int season) {
        // Get current ranked info for tier/division
        var rankedInfoList = rankedService.getRankedInfo(puuid);
        var soloRanked = rankedInfoList.stream()
                .filter(r -> "RANKED_SOLO_5x5".equals(r.getQueueType()))
                .findFirst()
                .orElse(null);

        String tier = soloRanked != null ? soloRanked.getTier() : "UNRANKED";
        String division = soloRanked != null ? soloRanked.getRank() : "";

        SeasonStats stats = new SeasonStats(season, tier, division, puuid);

        try {
            String version = dataDragonService.getLatestVersion();

            // Fetch ranked match IDs (queue 420 = Solo/Duo)
            // Get up to 100 recent matches
            String url = MATCH_IDS_URL + puuid + "/ids?queue=420&count=100&api_key=" + apiKey;

            String[] matchIds = restTemplate.getForObject(url, String[].class);

            if (matchIds == null || matchIds.length == 0) {
                System.out.println("No ranked matches found for player");
                return stats;
            }

            System.out.println("Found " + matchIds.length + " ranked matches");

            Map<String, ChampionSeasonStats> championStatsMap = new HashMap<>();

            // Process matches (limit to 50 for performance)
            int matchesToProcess = Math.min(matchIds.length, 50);

            for (int i = 0; i < matchesToProcess; i++) {
                try {
                    String matchUrl = MATCH_DETAIL_URL + matchIds[i] + "?api_key=" + apiKey;

                    @SuppressWarnings("unchecked")
                    Map<String, Object> matchData = restTemplate.getForObject(matchUrl, Map.class);

                    if (matchData != null) {
                        processMatch(matchData, puuid, championStatsMap, stats, version);
                    }

                    // Rate limiting - wait 50ms between requests
                    Thread.sleep(50);
                } catch (Exception e) {
                    System.err.println("Error processing match " + matchIds[i] + ": " + e.getMessage());
                }
            }

            // Set champion stats and sort by games played
            stats.setChampionStats(new ArrayList<>(championStatsMap.values()));
            stats.getChampionStats().sort((a, b) -> b.getGamesPlayed() - a.getGamesPlayed());

            // Calculate overall win rate
            if (stats.getTotalGames() > 0) {
                stats.setOverallWinRate((stats.getTotalWins() * 100.0) / stats.getTotalGames());
            }

            System.out.println("Processed " + stats.getTotalGames() + " games with " +
                    stats.getChampionStats().size() + " unique champions");

        } catch (Exception e) {
            System.err.println("Error fetching season stats: " + e.getMessage());
            e.printStackTrace();
        }

        return stats;
    }

    @SuppressWarnings("unchecked")
    private void processMatch(Map<String, Object> matchData, String puuid,
            Map<String, ChampionSeasonStats> championStatsMap,
            SeasonStats stats, String version) {
        Map<String, Object> info = (Map<String, Object>) matchData.get("info");
        if (info == null)
            return;

        List<Map<String, Object>> participants = (List<Map<String, Object>>) info.get("participants");
        if (participants == null)
            return;

        for (Map<String, Object> participant : participants) {
            String participantPuuid = (String) participant.get("puuid");
            if (puuid.equals(participantPuuid)) {
                String championName = (String) participant.get("championName");
                String championId = String.valueOf(participant.get("championId"));
                boolean win = Boolean.TRUE.equals(participant.get("win"));

                int kills = getIntValue(participant, "kills");
                int deaths = getIntValue(participant, "deaths");
                int assists = getIntValue(participant, "assists");
                int cs = getIntValue(participant, "totalMinionsKilled")
                        + getIntValue(participant, "neutralMinionsKilled");
                int doubles = getIntValue(participant, "doubleKills");
                int triples = getIntValue(participant, "tripleKills");
                int quadras = getIntValue(participant, "quadraKills");
                int pentas = getIntValue(participant, "pentaKills");

                // Update champion stats
                ChampionSeasonStats champStats = championStatsMap.computeIfAbsent(championName,
                        k -> new ChampionSeasonStats(
                                championId,
                                championName,
                                "https://ddragon.leagueoflegends.com/cdn/" + version + "/img/champion/" + championName
                                        + ".png"));

                champStats.addGame(win, kills, deaths, assists, cs, doubles, triples, quadras, pentas);

                // Update total stats
                stats.setTotalGames(stats.getTotalGames() + 1);
                if (win)
                    stats.setTotalWins(stats.getTotalWins() + 1);
                else
                    stats.setTotalLosses(stats.getTotalLosses() + 1);

                stats.setTotalDoubleKills(stats.getTotalDoubleKills() + doubles);
                stats.setTotalTripleKills(stats.getTotalTripleKills() + triples);
                stats.setTotalQuadraKills(stats.getTotalQuadraKills() + quadras);
                stats.setTotalPentaKills(stats.getTotalPentaKills() + pentas);

                // Update avg CS
                double currentAvgCs = stats.getAvgCs();
                int totalGames = stats.getTotalGames();
                stats.setAvgCs(((currentAvgCs * (totalGames - 1)) + cs) / totalGames);

                break;
            }
        }
    }

    private int getIntValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return 0;
    }
}
