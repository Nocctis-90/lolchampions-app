package com.example.lol.model;

import lombok.Data;

@Data
public class Item {
    private String name;
    private String description;
    private String plaintext;
    private Image image;
    private Gold gold;
    private String[] tags;
}
