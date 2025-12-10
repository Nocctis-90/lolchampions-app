package com.example.lol.model;

/**
 * Represents historical rank information for a specific season
 */
public class SeasonRank {
    private int season; // Season number (e.g., 2023, 2024)
    private String tier; // IRON, BRONZE, SILVER, GOLD, etc.
    private String division; // I, II, III, IV
    private String queueType; // RANKED_SOLO_5x5, RANKED_FLEX_SR

    public SeasonRank() {
    }

    public SeasonRank(int season, String tier, String division, String queueType) {
        this.season = season;
        this.tier = tier;
        this.division = division;
        this.queueType = queueType;
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

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public String getFullRank() {
        if (tier == null || tier.equals("UNRANKED"))
            return "Unranked";
        if (tier.equals("MASTER") || tier.equals("GRANDMASTER") || tier.equals("CHALLENGER")) {
            return tier;
        }
        return tier + " " + division;
    }
}
