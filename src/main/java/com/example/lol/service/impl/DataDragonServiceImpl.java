package com.example.lol.service.impl;

import com.example.lol.service.DataDragonService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of DataDragonService.
 * Handles version fetching from Data Dragon API.
 */
@Service
public class DataDragonServiceImpl implements DataDragonService {

    private final RestTemplate restTemplate;

    private static final String VERSION_URL = "https://ddragon.leagueoflegends.com/api/versions.json";
    private static final String FALLBACK_VERSION = "15.24.1";

    public DataDragonServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getLatestVersion() {
        try {
            String[] versions = restTemplate.getForObject(VERSION_URL, String[].class);
            if (versions != null && versions.length > 0) {
                return versions[0];
            }
        } catch (Exception e) {
            System.err.println("Error fetching version: " + e.getMessage());
        }
        return FALLBACK_VERSION;
    }

    @Override
    public String getVersion() {
        return getLatestVersion();
    }
}
