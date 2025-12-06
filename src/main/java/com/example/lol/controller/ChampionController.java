package com.example.lol.controller;

import com.example.lol.service.LolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChampionController {

    private final LolService lolService;

    public ChampionController(LolService lolService) {
        this.lolService = lolService;
    }

    @GetMapping("/")
    public String getChampions(Model model) {
        model.addAttribute("champions", lolService.getChampions());
        model.addAttribute("version", lolService.getVersion());
        return "champions";
    }

    @GetMapping("/champions/{id}")
    public String getChampionDetail(@PathVariable String id, Model model) {
        model.addAttribute("champion", lolService.getChampionDetail(id));
        model.addAttribute("version", lolService.getVersion());
        return "detail";
    }
}
