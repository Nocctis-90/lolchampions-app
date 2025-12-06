package com.example.lol.model;

import lombok.Data;
import java.util.List;

@Data
public class ChampionDetail {
    private String id;
    private String key;
    private String name;
    private String title;
    private Image image;
    private List<String> tags;
    private String partype;
    private String lore;
    private Passive passive;
    private List<Spell> spells;
    private List<Skin> skins;
    private String blurb;
}
