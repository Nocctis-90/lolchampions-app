package com.example.lol.model;

public class PlayerInfo {
    private String gameName;
    private String tagLine;
    private String puuid;
    private int profileIconId;
    private long summonerLevel;
    private String profileIconUrl;

    public PlayerInfo() {
    }

    public PlayerInfo(String gameName, String tagLine, String puuid, int profileIconId, long summonerLevel,
            String profileIconUrl) {
        this.gameName = gameName;
        this.tagLine = tagLine;
        this.puuid = puuid;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.profileIconUrl = profileIconUrl;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }

    public long getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(long summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public String getProfileIconUrl() {
        return profileIconUrl;
    }

    public void setProfileIconUrl(String profileIconUrl) {
        this.profileIconUrl = profileIconUrl;
    }
}
