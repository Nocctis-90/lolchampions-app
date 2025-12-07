package com.example.lol.helper;

import com.example.lol.model.Champion;
import com.example.lol.model.ChampionRotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChampionHelperTest {

    private ChampionHelper championHelper;

    @BeforeEach
    void setUp() {
        championHelper = new ChampionHelper();
    }

    private Champion createChampion(String id, String name, String key, String... tags) {
        Champion champion = new Champion();
        champion.setId(id);
        champion.setName(name);
        champion.setKey(key);
        champion.setTags(Arrays.asList(tags));
        return champion;
    }

    @Test
    void filterByRotation_shouldFilterCorrectly() {
        Champion ahri = createChampion("Ahri", "Ahri", "103", "Mage");
        Champion zed = createChampion("Zed", "Zed", "238", "Assassin");
        Champion jinx = createChampion("Jinx", "Jinx", "222", "Marksman");
        List<Champion> allChampions = Arrays.asList(ahri, zed, jinx);

        ChampionRotation rotation = new ChampionRotation();
        rotation.setFreeChampionIds(Arrays.asList(103, 222)); // Ahri and Jinx

        List<Champion> result = championHelper.filterByRotation(allChampions, rotation);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(c -> "Ahri".equals(c.getName())));
        assertTrue(result.stream().anyMatch(c -> "Jinx".equals(c.getName())));
        assertFalse(result.stream().anyMatch(c -> "Zed".equals(c.getName())));
    }

    @Test
    void filterByRotation_shouldReturnEmptyListWhenRotationIsNull() {
        Champion ahri = createChampion("Ahri", "Ahri", "103", "Mage");
        List<Champion> allChampions = Arrays.asList(ahri);

        List<Champion> result = championHelper.filterByRotation(allChampions, null);

        assertTrue(result.isEmpty());
    }

    @Test
    void filterByRotation_shouldReturnEmptyListWhenChampionsIsNull() {
        ChampionRotation rotation = new ChampionRotation();
        rotation.setFreeChampionIds(Arrays.asList(103));

        List<Champion> result = championHelper.filterByRotation(null, rotation);

        assertTrue(result.isEmpty());
    }

    @Test
    void filterByTag_shouldFilterByTag() {
        Champion ahri = createChampion("Ahri", "Ahri", "103", "Mage", "Assassin");
        Champion zed = createChampion("Zed", "Zed", "238", "Assassin");
        Champion jinx = createChampion("Jinx", "Jinx", "222", "Marksman");
        List<Champion> champions = Arrays.asList(ahri, zed, jinx);

        List<Champion> result = championHelper.filterByTag(champions, "Assassin");

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(c -> "Ahri".equals(c.getName())));
        assertTrue(result.stream().anyMatch(c -> "Zed".equals(c.getName())));
    }

    @Test
    void filterByTag_shouldReturnAllWhenTagIsNull() {
        Champion ahri = createChampion("Ahri", "Ahri", "103", "Mage");
        List<Champion> champions = Arrays.asList(ahri);

        List<Champion> result = championHelper.filterByTag(champions, null);

        assertEquals(1, result.size());
    }

    @Test
    void sortByName_shouldSortAlphabetically() {
        Champion zed = createChampion("Zed", "Zed", "238", "Assassin");
        Champion ahri = createChampion("Ahri", "Ahri", "103", "Mage");
        Champion jinx = createChampion("Jinx", "Jinx", "222", "Marksman");
        List<Champion> champions = Arrays.asList(zed, ahri, jinx);

        List<Champion> result = championHelper.sortByName(champions);

        assertEquals("Ahri", result.get(0).getName());
        assertEquals("Jinx", result.get(1).getName());
        assertEquals("Zed", result.get(2).getName());
    }

    @Test
    void sortByName_shouldReturnEmptyListWhenNull() {
        List<Champion> result = championHelper.sortByName(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void searchByName_shouldFindPartialMatch() {
        Champion ahri = createChampion("Ahri", "Ahri", "103", "Mage");
        Champion masterYi = createChampion("MasterYi", "Master Yi", "11", "Assassin");
        List<Champion> champions = Arrays.asList(ahri, masterYi);

        List<Champion> result = championHelper.searchByName(champions, "master");

        assertEquals(1, result.size());
        assertEquals("Master Yi", result.get(0).getName());
    }

    @Test
    void searchByName_shouldBeCaseInsensitive() {
        Champion ahri = createChampion("Ahri", "Ahri", "103", "Mage");
        List<Champion> champions = Arrays.asList(ahri);

        List<Champion> result = championHelper.searchByName(champions, "AHRI");

        assertEquals(1, result.size());
    }

    @Test
    void findById_shouldFindChampion() {
        Champion ahri = createChampion("Ahri", "Ahri", "103", "Mage");
        Champion zed = createChampion("Zed", "Zed", "238", "Assassin");
        List<Champion> champions = Arrays.asList(ahri, zed);

        Champion result = championHelper.findById(champions, "Zed");

        assertNotNull(result);
        assertEquals("Zed", result.getName());
    }

    @Test
    void findById_shouldReturnNullWhenNotFound() {
        Champion ahri = createChampion("Ahri", "Ahri", "103", "Mage");
        List<Champion> champions = Arrays.asList(ahri);

        Champion result = championHelper.findById(champions, "Unknown");

        assertNull(result);
    }
}
