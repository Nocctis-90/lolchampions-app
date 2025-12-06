package com.example.lol.model;

import lombok.Data;
import java.util.Map;

@Data
public class ChampionResponse {
    private String type;
    private String format;
    private String version;
    private Map<String, Champion> data;
}
