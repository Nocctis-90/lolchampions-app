package com.example.lol.model;

import java.util.List;

public class Match {
    private String matchId;
    private String championName;
    private String championImageUrl;
    private boolean win;
    private boolean isRemake;
    private String gameMode;
    private String queueType;
    private String kda;
    private String gameDate;
    private int gameDurationMinutes;
    private int gameDurationSeconds;
    private List<MatchParticipantSummary> team1;
    private List<MatchParticipantSummary> team2;

    public Match(String matchId, String championName, String championImageUrl, boolean win, boolean isRemake,
            String gameMode, String queueType, String kda, String gameDate, int gameDurationMinutes,
            int gameDurationSeconds, List<MatchParticipantSummary> team1,
            List<MatchParticipantSummary> team2) {
        this.matchId = matchId;
        this.championName = championName;
        this.championImageUrl = championImageUrl;
        this.win = win;
        this.isRemake = isRemake;
        this.gameMode = gameMode;
        this.queueType = queueType;
        this.kda = kda;
        this.gameDate = gameDate;
        this.gameDurationMinutes = gameDurationMinutes;
        this.gameDurationSeconds = gameDurationSeconds;
        this.team1 = team1;
        this.team2 = team2;
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

    public String getChampionImageUrl() {
        return championImageUrl;
    }

    public void setChampionImageUrl(String championImageUrl) {
        this.championImageUrl = championImageUrl;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isRemake() {
        return isRemake;
    }

    public void setRemake(boolean remake) {
        isRemake = remake;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
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

    public int getGameDurationMinutes() {
        return gameDurationMinutes;
    }

    public void setGameDurationMinutes(int gameDurationMinutes) {
        this.gameDurationMinutes = gameDurationMinutes;
    }

    public int getGameDurationSeconds() {
        return gameDurationSeconds;
    }

    public void setGameDurationSeconds(int gameDurationSeconds) {
        this.gameDurationSeconds = gameDurationSeconds;
    }

    public List<MatchParticipantSummary> getTeam1() {
        return team1;
    }

    public void setTeam1(List<MatchParticipantSummary> team1) {
        this.team1 = team1;
    }

    public List<MatchParticipantSummary> getTeam2() {
        return team2;
    }

    public void setTeam2(List<MatchParticipantSummary> team2) {
        this.team2 = team2;
    }
}
