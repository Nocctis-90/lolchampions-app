package com.example.lol.controller;

import com.example.lol.model.Match;
import com.example.lol.model.PlayerInfo;
import com.example.lol.service.LolService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final LolService lolService;

    public MatchController(LolService lolService) {
        this.lolService = lolService;
    }

    @GetMapping("/{gameName}/{tag}")
    public List<Match> getMatches(@PathVariable String gameName, @PathVariable String tag) {
        return lolService.getMatches(gameName, tag);
    }

    @GetMapping("/player/{gameName}/{tag}")
    public PlayerInfo getPlayerInfo(@PathVariable String gameName, @PathVariable String tag) {
        return lolService.getPlayerInfo(gameName, tag);
    }
}
