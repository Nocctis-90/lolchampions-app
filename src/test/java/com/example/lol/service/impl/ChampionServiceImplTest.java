package com.example.lol.service.impl;

import com.example.lol.helper.ChampionHelper;
import com.example.lol.helper.RiotApiHelper;
import com.example.lol.model.*;
import com.example.lol.service.DataDragonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChampionServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private DataDragonService dataDragonService;

    @Mock
    private RiotApiHelper riotApiHelper;

    @Mock
    private ChampionHelper championHelper;

    private ChampionServiceImpl championService;

    @BeforeEach
    void setUp() {
        championService = new ChampionServiceImpl(restTemplate, dataDragonService, riotApiHelper, championHelper);
    }

    @Test
    void getAllChampions_shouldReturnListOfChampions() {
        when(dataDragonService.getLatestVersion()).thenReturn("15.24.1");

        ChampionResponse response = new ChampionResponse();
        Map<String, Champion> data = new HashMap<>();
        Champion ahri = new Champion();
        ahri.setId("Ahri");
        ahri.setName("Ahri");
        data.put("Ahri", ahri);
        response.setData(data);

        when(restTemplate.getForObject(contains("champion.json"), eq(ChampionResponse.class))).thenReturn(response);

        List<Champion> result = championService.getAllChampions();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Ahri", result.get(0).getName());
    }

    @Test
    void getAllChampions_shouldReturnEmptyListWhenNull() {
        when(dataDragonService.getLatestVersion()).thenReturn("15.24.1");
        when(restTemplate.getForObject(contains("champion.json"), eq(ChampionResponse.class))).thenReturn(null);

        List<Champion> result = championService.getAllChampions();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getChampionDetail_shouldReturnChampionDetail() {
        when(dataDragonService.getLatestVersion()).thenReturn("15.24.1");

        ChampionDetailResponse response = new ChampionDetailResponse();
        Map<String, ChampionDetail> data = new HashMap<>();
        ChampionDetail ahri = new ChampionDetail();
        ahri.setId("Ahri");
        ahri.setLore("Test lore");
        data.put("Ahri", ahri);
        response.setData(data);

        when(restTemplate.getForObject(contains("Ahri.json"), eq(ChampionDetailResponse.class))).thenReturn(response);

        ChampionDetail result = championService.getChampionDetail("Ahri");

        assertNotNull(result);
        assertEquals("Test lore", result.getLore());
    }

    @Test
    void getChampionRotation_shouldReturnRotation() {
        ChampionRotation rotation = new ChampionRotation();
        rotation.setFreeChampionIds(Arrays.asList(103, 1, 2));

        when(riotApiHelper.buildChampionRotationUrl()).thenReturn("https://test.url");
        when(riotApiHelper.executeGet(anyString(), eq(ChampionRotation.class))).thenReturn(rotation);

        ChampionRotation result = championService.getChampionRotation();

        assertNotNull(result);
        assertEquals(3, result.getFreeChampionIds().size());
    }

    @Test
    void getFreeChampions_shouldUseChampionHelper() {
        when(dataDragonService.getLatestVersion()).thenReturn("15.24.1");

        ChampionRotation rotation = new ChampionRotation();
        rotation.setFreeChampionIds(Arrays.asList(103));

        ChampionResponse response = new ChampionResponse();
        Map<String, Champion> data = new HashMap<>();
        Champion ahri = new Champion();
        ahri.setId("Ahri");
        ahri.setKey("103");
        data.put("Ahri", ahri);
        response.setData(data);

        when(riotApiHelper.buildChampionRotationUrl()).thenReturn("https://rotation.url");
        when(riotApiHelper.executeGet(eq("https://rotation.url"), eq(ChampionRotation.class))).thenReturn(rotation);
        when(restTemplate.getForObject(contains("champion.json"), eq(ChampionResponse.class))).thenReturn(response);
        when(championHelper.filterByRotation(anyList(), eq(rotation))).thenReturn(Arrays.asList(ahri));

        List<Champion> result = championService.getFreeChampions();

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
