package com.example.lol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChampionRotation {
    private List<Integer> freeChampionIds;
    private List<Integer> freeChampionIdsForNewPlayers;
    private int maxNewPlayerLevel;

    public List<Integer> getFreeChampionIds() {
        return freeChampionIds;
    }

    public void setFreeChampionIds(List<Integer> freeChampionIds) {
        this.freeChampionIds = freeChampionIds;
    }

    public List<Integer> getFreeChampionIdsForNewPlayers() {
        return freeChampionIdsForNewPlayers;
    }

    public void setFreeChampionIdsForNewPlayers(List<Integer> freeChampionIdsForNewPlayers) {
        this.freeChampionIdsForNewPlayers = freeChampionIdsForNewPlayers;
    }

    public int getMaxNewPlayerLevel() {
        return maxNewPlayerLevel;
    }

    public void setMaxNewPlayerLevel(int maxNewPlayerLevel) {
        this.maxNewPlayerLevel = maxNewPlayerLevel;
    }
}
