package com.example.lol.model;

import lombok.Data;
import java.util.List;

@Data
public class Spell {
    private String id;
    private String name;
    private String description;
    private String tooltip;
    private Image image;
    private List<Integer> cooldown;
    private String costType;
    private List<Integer> cost;
}
