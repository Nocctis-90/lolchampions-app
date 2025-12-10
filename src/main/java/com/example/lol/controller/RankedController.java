package com.example.lol.controller;

import com.example.lol.model.RankedInfo;
import com.example.lol.service.RankedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranked")
@CrossOrigin(origins = "http://localhost:4200")
public class RankedController {

    @Autowired
    private RankedService rankedService;

    @GetMapping("/{puuid}")
    public List<RankedInfo> getRankedInfo(@PathVariable String puuid) {
        return rankedService.getRankedInfo(puuid);
    }
}
