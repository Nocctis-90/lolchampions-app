package com.example.lol.helper;

import com.example.lol.model.Match;
import com.example.lol.model.MatchDetail;
import com.example.lol.model.MatchParticipant;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper class for Match-related business logic.
 * Handles data transformation and extraction from match data.
 */
@Component
public class MatchHelper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

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

        return new Match(
                matchId,
                participant.getChampionName(),
                participant.isWin(),
                matchDetail.getInfo().getGameMode(),
                kda,
                gameDate);
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
