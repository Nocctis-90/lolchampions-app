package com.example.lol.model;

public class Match {
    private String matchId;
    private String championName;
    private boolean win;
    private String gameMode;
    private String kda;
    private String gameDate;

    public Match(String matchId, String championName, boolean win, String gameMode, String kda, String gameDate) {
        this.matchId = matchId;
        this.championName = championName;
        this.win = win;
        this.gameMode = gameMode;
        this.kda = kda;
        this.gameDate = gameDate;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getKda() {
        return kda;
    }

    public void setKda(String kda) {
        this.kda = kda;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }
}
