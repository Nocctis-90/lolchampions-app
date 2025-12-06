package com.example.lol.model;

import lombok.Data;
import java.util.List;

@Data
public class Champion {
    private String id;
    private String key;
    private String name;
    private String title;
    private String blurb;
    private Image image;
    private List<String> tags;
    private String partype;
}
