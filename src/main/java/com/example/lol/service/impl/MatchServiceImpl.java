package com.example.lol.service.impl;

import com.example.lol.helper.MatchHelper;
import com.example.lol.helper.RiotApiHelper;
import com.example.lol.model.*;
import com.example.lol.service.MatchService;
import com.example.lol.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of MatchService.
 * Handles match data operations from Riot API.
 */
@Service
public class MatchServiceImpl implements MatchService {

    private final RiotApiHelper riotApiHelper;
    private final MatchHelper matchHelper;
    private final PlayerService playerService;

    public MatchServiceImpl(RiotApiHelper riotApiHelper,
            MatchHelper matchHelper,
            PlayerService playerService) {
        this.riotApiHelper = riotApiHelper;
        this.matchHelper = matchHelper;
        this.playerService = playerService;
    }

    @Override
    public List<Match> getMatches(String gameName, String tagLine) {
        try {
            // Step 1: Get PUUID from account
            RiotAccount account = playerService.getAccountByRiotId(gameName, tagLine);
            if (account == null) {
                return new ArrayList<>();
            }

            String puuid = account.getPuuid();

            // Step 2: Get match IDs
            String matchIdsUrl = riotApiHelper.buildMatchesByPuuidUrl(puuid, 0, 10);
            String[] matchIds = riotApiHelper.executeGet(matchIdsUrl, String[].class);

            if (matchIds == null) {
                return new ArrayList<>();
            }

            // Step 3: Get details for each match
            List<Match> matches = new ArrayList<>();
            for (String matchId : matchIds) {
                Match match = getMatchDetails(matchId, puuid);
                if (match != null) {
                    matches.add(match);
                }
            }

            return matches;
        } catch (Exception e) {
            logError("fetching matches", e);
            return new ArrayList<>();
        }
    }

    private Match getMatchDetails(String matchId, String puuid) {
        try {
            String url = riotApiHelper.buildMatchByIdUrl(matchId);
            MatchDetail matchDetail = riotApiHelper.executeGet(url, MatchDetail.class);

            MatchParticipant participant = matchHelper.findParticipantByPuuid(matchDetail, puuid);
            return matchHelper.createMatch(matchId, matchDetail, participant);
        } catch (Exception e) {
            logError("fetching match details for " + matchId, e);
            return null;
        }
    }

    private void logError(String operation, Exception e) {
        System.err.println("Error " + operation + ": " + e.getMessage());
    }
}
