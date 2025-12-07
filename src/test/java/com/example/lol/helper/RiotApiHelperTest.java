package com.example.lol.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RiotApiHelperTest {

    @Mock
    private RestTemplate restTemplate;

    private RiotApiHelper riotApiHelper;

    @BeforeEach
    void setUp() {
        riotApiHelper = new RiotApiHelper(restTemplate);
        ReflectionTestUtils.setField(riotApiHelper, "riotApiKey", "test-api-key");
    }

    @Test
    void createAuthHeaders_shouldContainApiKey() {
        HttpHeaders headers = riotApiHelper.createAuthHeaders();

        assertNotNull(headers);
        assertEquals("test-api-key", headers.getFirst("X-Riot-Token"));
    }

    @Test
    void createAuthEntity_shouldContainHeaders() {
        HttpEntity<String> entity = riotApiHelper.createAuthEntity();

        assertNotNull(entity);
        assertNotNull(entity.getHeaders());
        assertEquals("test-api-key", entity.getHeaders().getFirst("X-Riot-Token"));
    }

    @Test
    void buildAccountByRiotIdUrl_shouldFormatCorrectly() {
        String url = riotApiHelper.buildAccountByRiotIdUrl("TestPlayer", "BR1");

        assertEquals("https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/TestPlayer/BR1", url);
    }

    @Test
    void buildSummonerByPuuidUrl_shouldFormatCorrectly() {
        String url = riotApiHelper.buildSummonerByPuuidUrl("test-puuid-123");

        assertEquals("https://br1.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/test-puuid-123", url);
    }

    @Test
    void buildMatchesByPuuidUrl_shouldFormatCorrectly() {
        String url = riotApiHelper.buildMatchesByPuuidUrl("test-puuid", 0, 10);

        assertEquals("https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/test-puuid/ids?start=0&count=10",
                url);
    }

    @Test
    void buildMatchByIdUrl_shouldFormatCorrectly() {
        String url = riotApiHelper.buildMatchByIdUrl("BR1_123456");

        assertEquals("https://americas.api.riotgames.com/lol/match/v5/matches/BR1_123456", url);
    }

    @Test
    void buildChampionRotationUrl_shouldReturnCorrectUrl() {
        String url = riotApiHelper.buildChampionRotationUrl();

        assertEquals("https://br1.api.riotgames.com/lol/platform/v3/champion-rotations", url);
    }

    @Test
    void buildProfileIconUrl_shouldFormatCorrectly() {
        String url = riotApiHelper.buildProfileIconUrl("14.23.1", 4567);

        assertEquals("https://ddragon.leagueoflegends.com/cdn/14.23.1/img/profileicon/4567.png", url);
    }

    @Test
    void buildChampionImageUrl_shouldFormatCorrectly() {
        String url = riotApiHelper.buildChampionImageUrl("14.23.1", "Ahri.png");

        assertEquals("https://ddragon.leagueoflegends.com/cdn/14.23.1/img/champion/Ahri.png", url);
    }

    @Test
    void executeGet_shouldCallRestTemplateExchange() {
        ResponseEntity<String> mockResponse = ResponseEntity.ok("test-response");
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(mockResponse);

        String result = riotApiHelper.executeGet("https://test.url", String.class);

        assertEquals("test-response", result);
    }
}
