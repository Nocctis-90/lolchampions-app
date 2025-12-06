package com.example.lol.controller;

import com.example.lol.service.LolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    private final LolService lolService;

    public MenuController(LolService lolService) {
        this.lolService = lolService;
    }

    @GetMapping("/")
    public String getMenu(Model model) {
        model.addAttribute("version", lolService.getVersion());
        return "menu";
    }

    @GetMapping("/items")
    public String getItems(Model model) {
        model.addAttribute("items", lolService.getItems());
        model.addAttribute("version", lolService.getVersion());
        return "items";
    }
}
