package com.example.lol.service.impl;

import com.example.lol.helper.MatchHelper;
import com.example.lol.helper.RiotApiHelper;
import com.example.lol.model.*;
import com.example.lol.service.MatchService;
import com.example.lol.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Implementation of MatchService.
 * Handles match data operations from Riot API.
 */
@Service
public class MatchServiceImpl implements MatchService {

    private final RiotApiHelper riotApiHelper;
    private final MatchHelper matchHelper;
    private final PlayerService playerService;
    private final ExecutorService executorService;

    public MatchServiceImpl(RiotApiHelper riotApiHelper,
            MatchHelper matchHelper,
            PlayerService playerService) {
        this.riotApiHelper = riotApiHelper;
        this.matchHelper = matchHelper;
        this.playerService = playerService;
        this.executorService = Executors.newFixedThreadPool(10);
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

            // Step 3: Get details for each match IN PARALLEL
            List<CompletableFuture<Match>> futureMatches = new ArrayList<>();

            for (String matchId : matchIds) {
                CompletableFuture<Match> future = CompletableFuture.supplyAsync(
                        () -> getMatchDetails(matchId, puuid),
                        executorService);
                futureMatches.add(future);
            }

            // Wait for all futures to complete and collect results
            List<Match> matches = futureMatches.stream()
                    .map(CompletableFuture::join)
                    .filter(match -> match != null)
                    .collect(Collectors.toList());

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
