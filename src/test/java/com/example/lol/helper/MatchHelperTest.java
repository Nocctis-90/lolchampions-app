package com.example.lol.helper;

import com.example.lol.model.Match;
import com.example.lol.model.MatchDetail;
import com.example.lol.model.MatchInfo;
import com.example.lol.model.MatchParticipant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MatchHelperTest {

    private MatchHelper matchHelper;

    @BeforeEach
    void setUp() {
        matchHelper = new MatchHelper();
    }

    @Test
    void buildKdaString_shouldFormatCorrectly() {
        String kda = matchHelper.buildKdaString(10, 2, 5);
        assertEquals("10/2/5", kda);
    }

    @Test
    void buildKdaString_withZeros_shouldFormatCorrectly() {
        String kda = matchHelper.buildKdaString(0, 0, 0);
        assertEquals("0/0/0", kda);
    }

    @Test
    void formatGameDate_shouldReturnFormattedDate() {
        // 2024-01-15 00:00:00 UTC in milliseconds
        long timestamp = 1705276800000L;
        String date = matchHelper.formatGameDate(timestamp);
        assertNotNull(date);
        assertTrue(date.matches("\\d{4}-\\d{2}-\\d{2}"));
    }

    @Test
    void findParticipantByPuuid_shouldFindCorrectParticipant() {
        MatchParticipant p1 = new MatchParticipant();
        p1.setPuuid("puuid-1");
        p1.setChampionName("Ahri");

        MatchParticipant p2 = new MatchParticipant();
        p2.setPuuid("puuid-2");
        p2.setChampionName("Zed");

        MatchInfo info = new MatchInfo();
        info.setParticipants(Arrays.asList(p1, p2));

        MatchDetail matchDetail = new MatchDetail();
        matchDetail.setInfo(info);

        MatchParticipant result = matchHelper.findParticipantByPuuid(matchDetail, "puuid-2");

        assertNotNull(result);
        assertEquals("Zed", result.getChampionName());
    }

    @Test
    void findParticipantByPuuid_shouldReturnNullWhenNotFound() {
        MatchParticipant p1 = new MatchParticipant();
        p1.setPuuid("puuid-1");

        MatchInfo info = new MatchInfo();
        info.setParticipants(Arrays.asList(p1));

        MatchDetail matchDetail = new MatchDetail();
        matchDetail.setInfo(info);

        MatchParticipant result = matchHelper.findParticipantByPuuid(matchDetail, "unknown");

        assertNull(result);
    }

    @Test
    void findParticipantByPuuid_shouldReturnNullWhenMatchDetailIsNull() {
        MatchParticipant result = matchHelper.findParticipantByPuuid(null, "puuid-1");
        assertNull(result);
    }

    @Test
    void createMatch_shouldCreateMatchFromParticipant() {
        MatchParticipant participant = new MatchParticipant();
        participant.setChampionName("Ahri");
        participant.setWin(true);
        participant.setKills(10);
        participant.setDeaths(2);
        participant.setAssists(5);

        MatchInfo info = new MatchInfo();
        info.setGameMode("CLASSIC");
        info.setGameCreation(1705276800000L);

        MatchDetail matchDetail = new MatchDetail();
        matchDetail.setInfo(info);

        Match match = matchHelper.createMatch("BR1_123", matchDetail, participant);

        assertNotNull(match);
        assertEquals("BR1_123", match.getMatchId());
        assertEquals("Ahri", match.getChampionName());
        assertTrue(match.isWin());
        assertEquals("10/2/5", match.getKda());
        assertEquals("CLASSIC", match.getGameMode());
    }

    @Test
    void createMatch_shouldReturnNullWhenParticipantIsNull() {
        MatchDetail matchDetail = new MatchDetail();
        Match match = matchHelper.createMatch("BR1_123", matchDetail, null);
        assertNull(match);
    }

    @Test
    void calculateKdaRatio_shouldCalculateCorrectly() {
        double ratio = matchHelper.calculateKdaRatio(10, 2, 5);
        assertEquals(7.5, ratio, 0.01);
    }

    @Test
    void calculateKdaRatio_withZeroDeaths_shouldReturnPerfectKda() {
        double ratio = matchHelper.calculateKdaRatio(10, 0, 5);
        assertEquals(15.0, ratio, 0.01);
    }

    @Test
    void isGoodKda_shouldReturnTrueForGoodKda() {
        assertTrue(matchHelper.isGoodKda(10, 2, 5)); // 7.5 KDA
        assertTrue(matchHelper.isGoodKda(6, 2, 0)); // 3.0 KDA
    }

    @Test
    void isGoodKda_shouldReturnFalseForBadKda() {
        assertFalse(matchHelper.isGoodKda(2, 5, 1)); // 0.6 KDA
        assertFalse(matchHelper.isGoodKda(0, 10, 0)); // 0 KDA
    }
}
