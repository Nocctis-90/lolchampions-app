package com.example.lol.controller;

import com.example.lol.model.Champion;
import com.example.lol.model.ChampionDetail;
import com.example.lol.model.ChampionRotation;
import com.example.lol.service.ChampionService;
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

@WebMvcTest(ChampionController.class)
class ChampionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChampionService championService;

    @Test
    void getChampions_shouldReturnListOfChampions() throws Exception {
        Champion ahri = new Champion();
        ahri.setId("Ahri");
        ahri.setName("Ahri");
        ahri.setTitle("the Nine-Tailed Fox");

        when(championService.getAllChampions()).thenReturn(Arrays.asList(ahri));

        mockMvc.perform(get("/api/champions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("Ahri"))
                .andExpect(jsonPath("$[0].name").value("Ahri"));
    }

    @Test
    void getChampions_shouldReturnEmptyList() throws Exception {
        when(championService.getAllChampions()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/champions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getChampionDetail_shouldReturnChampionDetail() throws Exception {
        ChampionDetail ahri = new ChampionDetail();
        ahri.setId("Ahri");
        ahri.setName("Ahri");
        ahri.setLore("Innately connected to the latent power of Runeterra...");

        when(championService.getChampionDetail("Ahri")).thenReturn(ahri);

        mockMvc.perform(get("/api/champions/Ahri"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("Ahri"))
                .andExpect(jsonPath("$.lore").exists());
    }

    @Test
    void getChampionDetail_shouldReturnNullWhenNotFound() throws Exception {
        when(championService.getChampionDetail("Unknown")).thenReturn(null);

        mockMvc.perform(get("/api/champions/Unknown"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void getFreeRotation_shouldReturnFreeChampions() throws Exception {
        Champion ahri = new Champion();
        ahri.setId("Ahri");
        ahri.setName("Ahri");
        ahri.setKey("103");

        when(championService.getFreeChampions()).thenReturn(Arrays.asList(ahri));

        mockMvc.perform(get("/api/champions/rotation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("Ahri"));
    }

    @Test
    void getRotationRaw_shouldReturnRotationData() throws Exception {
        ChampionRotation rotation = new ChampionRotation();
        rotation.setFreeChampionIds(Arrays.asList(103, 1, 2));
        rotation.setMaxNewPlayerLevel(10);

        when(championService.getChampionRotation()).thenReturn(rotation);

        mockMvc.perform(get("/api/champions/rotation/raw"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.freeChampionIds").isArray())
                .andExpect(jsonPath("$.maxNewPlayerLevel").value(10));
    }
}
