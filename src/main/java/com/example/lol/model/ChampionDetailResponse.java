package com.example.lol.model;

import lombok.Data;
import java.util.Map;

@Data
public class ChampionDetailResponse {
    private String type;
    private String format;
    private String version;
    private Map<String, ChampionDetail> data;
}
