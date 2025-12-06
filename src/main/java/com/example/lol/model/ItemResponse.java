package com.example.lol.model;

import lombok.Data;
import java.util.Map;

@Data
public class ItemResponse {
    private String type;
    private String version;
    private Map<String, Item> data;
}
