package com.example.lol.service.impl;

import com.example.lol.model.Item;
import com.example.lol.model.ItemResponse;
import com.example.lol.service.DataDragonService;
import com.example.lol.service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ItemService.
 * Handles item data operations from Data Dragon.
 */
@Service
public class ItemServiceImpl implements ItemService {

    private final RestTemplate restTemplate;
    private final DataDragonService dataDragonService;

    private static final String DDRAGON_BASE = "https://ddragon.leagueoflegends.com/cdn";
    private static final String ITEM_PATH = "/data/pt_BR/item.json";

    public ItemServiceImpl(RestTemplate restTemplate, DataDragonService dataDragonService) {
        this.restTemplate = restTemplate;
        this.dataDragonService = dataDragonService;
    }

    @Override
    public List<Item> getAllItems() {
        try {
            String version = dataDragonService.getLatestVersion();
            String url = DDRAGON_BASE + "/" + version + ITEM_PATH;
            ItemResponse response = restTemplate.getForObject(url, ItemResponse.class);

            if (response != null && response.getData() != null) {
                return new ArrayList<>(response.getData().values());
            }
        } catch (Exception e) {
            System.err.println("Error fetching items: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
