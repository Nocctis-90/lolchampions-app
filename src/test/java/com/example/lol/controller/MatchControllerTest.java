package com.example.lol.controller;

import com.example.lol.model.Match;
import com.example.lol.model.PlayerInfo;
import com.example.lol.service.MatchService;
import com.example.lol.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MatchController.class)
class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;

    @MockBean
    private PlayerService playerService;

    @Test
    void getMatches_shouldReturnListOfMatches() throws Exception {
        Match match = new Match("BR1_123456", "Ahri", true, "CLASSIC", "10/2/5", "2024-01-15");

        when(matchService.getMatches("TestPlayer", "BR1")).thenReturn(Arrays.asList(match));

        mockMvc.perform(get("/api/matches/TestPlayer/BR1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].matchId").value("BR1_123456"))
                .andExpect(jsonPath("$[0].championName").value("Ahri"))
                .andExpect(jsonPath("$[0].win").value(true))
                .andExpect(jsonPath("$[0].kda").value("10/2/5"));
    }

    @Test
    void getMatches_shouldReturnEmptyListWhenNoMatches() throws Exception {
        when(matchService.getMatches("NewPlayer", "BR1")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/matches/NewPlayer/BR1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getPlayerInfo_shouldReturnPlayerInfo() throws Exception {
        PlayerInfo playerInfo = new PlayerInfo(
                "TestPlayer",
                "BR1",
                "test-puuid-123",
                4567,
                150,
                "https://ddragon.leagueoflegends.com/cdn/15.24.1/img/profileicon/4567.png");

        when(playerService.getPlayerInfo("TestPlayer", "BR1")).thenReturn(playerInfo);

        mockMvc.perform(get("/api/matches/player/TestPlayer/BR1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameName").value("TestPlayer"))
                .andExpect(jsonPath("$.tagLine").value("BR1"))
                .andExpect(jsonPath("$.summonerLevel").value(150))
                .andExpect(jsonPath("$.profileIconUrl").exists());
    }

    @Test
    void getPlayerInfo_shouldReturnNullWhenPlayerNotFound() throws Exception {
        when(playerService.getPlayerInfo("Unknown", "XX1")).thenReturn(null);

        mockMvc.perform(get("/api/matches/player/Unknown/XX1"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
