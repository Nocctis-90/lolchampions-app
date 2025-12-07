package com.example.lol.controller;

import com.example.lol.model.Match;
import com.example.lol.model.PlayerInfo;
import com.example.lol.service.MatchService;
import com.example.lol.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Match and Player-related endpoints.
 */
@RestController
@RequestMapping("/api/matches")
@CrossOrigin(origins = "*")
public class MatchController {

    private final MatchService matchService;
    private final PlayerService playerService;

    public MatchController(MatchService matchService, PlayerService playerService) {
        this.matchService = matchService;
        this.playerService = playerService;
    }

    /**
     * Get match history for a player.
     */
    @GetMapping("/{gameName}/{tag}")
    public List<Match> getMatches(@PathVariable String gameName, @PathVariable String tag) {
        return matchService.getMatches(gameName, tag);
    }

    /**
     * Get player information (profile).
     */
    @GetMapping("/player/{gameName}/{tag}")
    public PlayerInfo getPlayerInfo(@PathVariable String gameName, @PathVariable String tag) {
        return playerService.getPlayerInfo(gameName, tag);
    }
}
