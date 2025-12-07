package com.example.lol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchDetail {
    private MatchInfo info;

    public MatchInfo getInfo() {
        return info;
    }

    public void setInfo(MatchInfo info) {
        this.info = info;
    }
}
