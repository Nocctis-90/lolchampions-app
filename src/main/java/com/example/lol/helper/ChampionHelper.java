package com.example.lol.helper;

import com.example.lol.model.Champion;
import com.example.lol.model.ChampionRotation;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class for Champion-related business logic.
 * Handles filtering, sorting, and data transformation for champions.
 */
@Component
public class ChampionHelper {

    /**
     * Filters champions by their IDs based on the rotation data.
     */
    public List<Champion> filterByRotation(List<Champion> allChampions, ChampionRotation rotation) {
        if (allChampions == null || rotation == null || rotation.getFreeChampionIds() == null) {
            return Collections.emptyList();
        }

        List<Integer> freeIds = rotation.getFreeChampionIds();

        return allChampions.stream()
                .filter(champion -> {
                    try {
                        int championKey = Integer.parseInt(champion.getKey());
                        return freeIds.contains(championKey);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * Filters champions by tag (e.g., "Mage", "Assassin", "Tank").
     */
    public List<Champion> filterByTag(List<Champion> champions, String tag) {
        if (champions == null || tag == null || tag.isEmpty()) {
            return champions;
        }

        return champions.stream()
                .filter(c -> c.getTags() != null && c.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    /**
     * Sorts champions alphabetically by name.
     */
    public List<Champion> sortByName(List<Champion> champions) {
        if (champions == null) {
            return Collections.emptyList();
        }

        return champions.stream()
                .sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Searches champions by name (case-insensitive partial match).
     */
    public List<Champion> searchByName(List<Champion> champions, String query) {
        if (champions == null || query == null || query.isEmpty()) {
            return champions;
        }

        String lowerQuery = query.toLowerCase();
        return champions.stream()
                .filter(c -> c.getName().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    /**
     * Gets a champion by ID from a list.
     */
    public Champion findById(List<Champion> champions, String id) {
        if (champions == null || id == null) {
            return null;
        }

        return champions.stream()
                .filter(c -> id.equals(c.getId()))
                .findFirst()
                .orElse(null);
    }
}
