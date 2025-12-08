package com.example.lol.model;

import java.util.List;

/**
 * Summary of a match participant for display purposes.
 */
public class MatchParticipantSummary {
    private String summonerName;
    private String tagline;
    private String championName;
    private String championImageUrl;
    private String kda;
    private boolean win;
    private boolean isCurrentPlayer;
    private List<String> itemImageUrls;
    private int farm;

    public MatchParticipantSummary(String summonerName, String tagline, String championName, String championImageUrl,
            String kda, boolean win, boolean isCurrentPlayer, List<String> itemImageUrls, int farm) {
        this.summonerName = summonerName;
        this.tagline = tagline;
        this.championName = championName;
        this.championImageUrl = championImageUrl;
        this.kda = kda;
        this.win = win;
        this.isCurrentPlayer = isCurrentPlayer;
        this.itemImageUrls = itemImageUrls;
        this.farm = farm;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public String getChampionImageUrl() {
        return championImageUrl;
    }

    public void setChampionImageUrl(String championImageUrl) {
        this.championImageUrl = championImageUrl;
    }

    public String getKda() {
        return kda;
    }

    public void setKda(String kda) {
        this.kda = kda;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isCurrentPlayer() {
        return isCurrentPlayer;
    }

    public void setCurrentPlayer(boolean currentPlayer) {
        isCurrentPlayer = currentPlayer;
    }

    public List<String> getItemImageUrls() {
        return itemImageUrls;
    }

    public void setItemImageUrls(List<String> itemImageUrls) {
        this.itemImageUrls = itemImageUrls;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }
}
