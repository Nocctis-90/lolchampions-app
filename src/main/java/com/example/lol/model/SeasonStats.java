package com.example.lol.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents overall season statistics for a player
 */
public class SeasonStats {
    private int season;
    private String tier;
    private String division;
    private String puuid;
    private int totalGames;
    private int totalWins;
    private int totalLosses;
    private double overallWinRate;
    private int totalDoubleKills;
    private int totalTripleKills;
    private int totalQuadraKills;
    private int totalPentaKills;
    private double avgCs;
    private List<ChampionSeasonStats> championStats;

    public SeasonStats() {
        this.championStats = new ArrayList<>();
    }

    public SeasonStats(int season, String tier, String division, String puuid) {
        this.season = season;
        this.tier = tier;
        this.division = division;
        this.puuid = puuid;
        this.totalGames = 0;
        this.totalWins = 0;
        this.totalLosses = 0;
        this.overallWinRate = 0;
        this.totalDoubleKills = 0;
        this.totalTripleKills = 0;
        this.totalQuadraKills = 0;
        this.totalPentaKills = 0;
        this.avgCs = 0;
        this.championStats = new ArrayList<>();
    }

    // Getters and Setters
    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public int getTotalLosses() {
        return totalLosses;
    }

    public void setTotalLosses(int totalLosses) {
        this.totalLosses = totalLosses;
    }

    public double getOverallWinRate() {
        return overallWinRate;
    }

    public void setOverallWinRate(double overallWinRate) {
        this.overallWinRate = overallWinRate;
    }

    public int getTotalDoubleKills() {
        return totalDoubleKills;
    }

    public void setTotalDoubleKills(int totalDoubleKills) {
        this.totalDoubleKills = totalDoubleKills;
    }

    public int getTotalTripleKills() {
        return totalTripleKills;
    }

    public void setTotalTripleKills(int totalTripleKills) {
        this.totalTripleKills = totalTripleKills;
    }

    public int getTotalQuadraKills() {
        return totalQuadraKills;
    }

    public void setTotalQuadraKills(int totalQuadraKills) {
        this.totalQuadraKills = totalQuadraKills;
    }

    public int getTotalPentaKills() {
        return totalPentaKills;
    }

    public void setTotalPentaKills(int totalPentaKills) {
        this.totalPentaKills = totalPentaKills;
    }

    public double getAvgCs() {
        return avgCs;
    }

    public void setAvgCs(double avgCs) {
        this.avgCs = avgCs;
    }

    public List<ChampionSeasonStats> getChampionStats() {
        return championStats;
    }

    public void setChampionStats(List<ChampionSeasonStats> championStats) {
        this.championStats = championStats;
    }

    public String getFullRank() {
        if (tier == null || tier.equals("UNRANKED"))
            return "Unranked";
        if (tier.equals("MASTER") || tier.equals("GRANDMASTER") || tier.equals("CHALLENGER")) {
            return tier;
        }
        return tier + " " + division;
    }

    public String getRankEmblemUrl() {
        if (tier == null || tier.equals("UNRANKED")) {
            return "https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-static-assets/global/default/images/ranked-emblem/emblem-iron.png";
        }
        return "https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-static-assets/global/default/images/ranked-emblem/emblem-"
                + tier.toLowerCase() + ".png";
    }
}
