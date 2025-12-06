package com.example.lol.model;

import lombok.Data;
import java.util.Map;

@Data
public class Item {
    private String name;
    private String description;
    private String plaintext;
    private Image image;
    private Map<String, Integer> gold;
    private String[] tags;
}
