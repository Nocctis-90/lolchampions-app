package com.example.lol.model;

/**
 * Represents statistics for a champion played in a specific season
 */
public class ChampionSeasonStats {
    private String championId;
    private String championName;
    private String championImageUrl;
    private int gamesPlayed;
    private int wins;
    private int losses;
    private double winRate;
    private double avgKills;
    private double avgDeaths;
    private double avgAssists;
    private double avgCs; // Minions farmados média
    private int doubleKills;
    private int tripleKills;
    private int quadraKills;
    private int pentaKills;

    public ChampionSeasonStats() {
    }

    public ChampionSeasonStats(String championId, String championName, String championImageUrl) {
        this.championId = championId;
        this.championName = championName;
        this.championImageUrl = championImageUrl;
        this.gamesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.winRate = 0;
        this.avgKills = 0;
        this.avgDeaths = 0;
        this.avgAssists = 0;
        this.avgCs = 0;
        this.doubleKills = 0;
        this.tripleKills = 0;
        this.quadraKills = 0;
        this.pentaKills = 0;
    }

    // Getters and Setters
    public String getChampionId() {
        return championId;
    }

    public void setChampionId(String championId) {
        this.championId = championId;
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

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }

    public double getAvgKills() {
        return avgKills;
    }

    public void setAvgKills(double avgKills) {
        this.avgKills = avgKills;
    }

    public double getAvgDeaths() {
        return avgDeaths;
    }

    public void setAvgDeaths(double avgDeaths) {
        this.avgDeaths = avgDeaths;
    }

    public double getAvgAssists() {
        return avgAssists;
    }

    public void setAvgAssists(double avgAssists) {
        this.avgAssists = avgAssists;
    }

    public double getAvgCs() {
        return avgCs;
    }

    public void setAvgCs(double avgCs) {
        this.avgCs = avgCs;
    }

    public int getDoubleKills() {
        return doubleKills;
    }

    public void setDoubleKills(int doubleKills) {
        this.doubleKills = doubleKills;
    }

    public int getTripleKills() {
        return tripleKills;
    }

    public void setTripleKills(int tripleKills) {
        this.tripleKills = tripleKills;
    }

    public int getQuadraKills() {
        return quadraKills;
    }

    public void setQuadraKills(int quadraKills) {
        this.quadraKills = quadraKills;
    }

    public int getPentaKills() {
        return pentaKills;
    }

    public void setPentaKills(int pentaKills) {
        this.pentaKills = pentaKills;
    }

    // Utility methods
    public void addGame(boolean win, int kills, int deaths, int assists, int cs,
            int doubles, int triples, int quadras, int pentas) {
        this.gamesPlayed++;
        if (win)
            this.wins++;
        else
            this.losses++;

        // Update averages
        this.avgKills = ((this.avgKills * (gamesPlayed - 1)) + kills) / gamesPlayed;
        this.avgDeaths = ((this.avgDeaths * (gamesPlayed - 1)) + deaths) / gamesPlayed;
        this.avgAssists = ((this.avgAssists * (gamesPlayed - 1)) + assists) / gamesPlayed;
        this.avgCs = ((this.avgCs * (gamesPlayed - 1)) + cs) / gamesPlayed;

        this.winRate = (this.wins * 100.0) / this.gamesPlayed;

        this.doubleKills += doubles;
        this.tripleKills += triples;
        this.quadraKills += quadras;
        this.pentaKills += pentas;
    }
}
