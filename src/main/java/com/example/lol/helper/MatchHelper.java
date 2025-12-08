package com.example.lol.helper;

import com.example.lol.model.Match;
import com.example.lol.model.MatchDetail;
import com.example.lol.model.MatchParticipant;
import com.example.lol.model.MatchParticipantSummary;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper class for Match-related business logic.
 * Handles data transformation and extraction from match data.
 */
@Component
public class MatchHelper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String CHAMPION_IMAGE_URL_TEMPLATE = "https://ddragon.leagueoflegends.com/cdn/14.23.1/img/champion/%s.png";

    /**
     * Extracts the participant data for a specific player from a match.
     */
    public MatchParticipant findParticipantByPuuid(MatchDetail matchDetail, String puuid) {
        if (matchDetail == null || matchDetail.getInfo() == null || matchDetail.getInfo().getParticipants() == null) {
            return null;
        }

        return matchDetail.getInfo().getParticipants().stream()
                .filter(p -> puuid.equals(p.getPuuid()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Builds KDA string from kills, deaths, and assists.
     */
    public String buildKdaString(int kills, int deaths, int assists) {
        return String.format("%d/%d/%d", kills, deaths, assists);
    }

    /**
     * Formats game creation timestamp to date string.
     */
    public String formatGameDate(long gameCreation) {
        return DATE_FORMAT.format(new Date(gameCreation));
    }

    /**
     * Builds champion image URL from champion name.
     */
    public String buildChampionImageUrl(String championName) {
        return String.format(CHAMPION_IMAGE_URL_TEMPLATE, championName);
    }

    /**
     * Creates a Match object from match detail and participant data.
     */
    public Match createMatch(String matchId, MatchDetail matchDetail, MatchParticipant participant) {
        if (participant == null || matchDetail == null || matchDetail.getInfo() == null) {
            return null;
        }

        String kda = buildKdaString(
                participant.getKills(),
                participant.getDeaths(),
                participant.getAssists());

        String gameDate = formatGameDate(matchDetail.getInfo().getGameCreation());
        String championImageUrl = buildChampionImageUrl(participant.getChampionName());
        String queueType = buildQueueType(matchDetail.getInfo().getQueueId());

        // Calculate duration
        long durationSeconds = matchDetail.getInfo().getGameDuration();
        int minutes = (int) (durationSeconds / 60);
        int seconds = (int) (durationSeconds % 60);

        // Detect remake (games < 5 minutes)
        boolean isRemake = durationSeconds < 300;

        // Build team lists
        List<MatchParticipantSummary> team1 = new ArrayList<>();
        List<MatchParticipantSummary> team2 = new ArrayList<>();

        for (MatchParticipant p : matchDetail.getInfo().getParticipants()) {
            MatchParticipantSummary summary = createParticipantSummary(p, participant.getPuuid());
            if (p.getTeamId() == 100) {
                team1.add(summary);
            } else {
                team2.add(summary);
            }
        }

        return new Match(
                matchId,
                participant.getChampionName(),
                championImageUrl,
                participant.isWin(),
                isRemake,
                matchDetail.getInfo().getGameMode(),
                queueType,
                kda,
                gameDate,
                minutes,
                seconds,
                team1,
                team2);
    }

    /**
     * Builds queue type name from queue ID.
     */
    public String buildQueueType(int queueId) {
        return switch (queueId) {
            case 420 -> "Ranked Solo/Duo";
            case 440 -> "Ranked Flex";
            case 450 -> "ARAM";
            case 400 -> "Normal Draft";
            case 430 -> "Normal Blind";
            case 700 -> "Clash";
            case 900 -> "ARURF";
            case 1020 -> "One for All";
            case 1300 -> "Nexus Blitz";
            case 1400 -> "Ultimate Spellbook";
            case 1700 -> "Arena";
            default -> "Custom / Other";
        };
    }

    /**
     * Creates a participant summary from participant data.
     */
    private MatchParticipantSummary createParticipantSummary(MatchParticipant p, String currentPuuid) {
        String displayName = p.getRiotIdGameName();
        if (displayName == null || displayName.isEmpty()) {
            displayName = "Unknown";
        }

        String tagline = p.getRiotIdTagline();
        if (tagline == null || tagline.isEmpty()) {
            tagline = "???";
        }

        List<String> itemUrls = buildItemImageUrls(p);
        int farm = p.getTotalMinionsKilled() + p.getNeutralMinionsKilled();

        return new MatchParticipantSummary(
                displayName,
                tagline,
                p.getChampionName(),
                buildChampionImageUrl(p.getChampionName()),
                buildKdaString(p.getKills(), p.getDeaths(), p.getAssists()),
                p.isWin(),
                p.getPuuid().equals(currentPuuid),
                itemUrls,
                farm);
    }

    /**
     * Builds item image URLs from participant item IDs.
     * Always returns 7 URLs (for items 0-6), using empty string for missing items.
     */
    private List<String> buildItemImageUrls(MatchParticipant p) {
        List<String> urls = new ArrayList<>();
        int[] items = { p.getItem0(), p.getItem1(), p.getItem2(), p.getItem3(), p.getItem4(), p.getItem5(),
                p.getItem6() };

        for (int itemId : items) {
            if (itemId > 0) {
                urls.add(String.format("https://ddragon.leagueoflegends.com/cdn/14.23.1/img/item/%d.png", itemId));
            } else {
                urls.add(""); // Empty slot
            }
        }

        return urls;
    }

    /**
     * Calculates KDA ratio.
     */
    public double calculateKdaRatio(int kills, int deaths, int assists) {
        if (deaths == 0) {
            return kills + assists; // Perfect KDA
        }
        return (double) (kills + assists) / deaths;
    }

    /**
     * Determines if the KDA is considered "good" (>= 3.0).
     */
    public boolean isGoodKda(int kills, int deaths, int assists) {
        return calculateKdaRatio(kills, deaths, assists) >= 3.0;
    }
}
