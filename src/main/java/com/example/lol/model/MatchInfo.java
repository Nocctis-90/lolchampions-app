package com.example.lol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchInfo {
    private long gameCreation;
    private long gameDuration;
    private String gameMode;
    private List<MatchParticipant> participants;

    public long getGameCreation() {
        return gameCreation;
    }

    public void setGameCreation(long gameCreation) {
        this.gameCreation = gameCreation;
    }

    public long getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(long gameDuration) {
        this.gameDuration = gameDuration;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public List<MatchParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<MatchParticipant> participants) {
        this.participants = participants;
    }
}
