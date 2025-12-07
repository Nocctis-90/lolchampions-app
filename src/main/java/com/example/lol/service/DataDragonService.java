package com.example.lol.service;

/**
 * Service interface for Data Dragon version operations.
 */
public interface DataDragonService {

    /**
     * Gets the latest game version from Data Dragon.
     * 
     * @return Latest version string (e.g., "15.24.1")
     */
    String getLatestVersion();

    /**
     * Alias for getLatestVersion.
     * 
     * @return Latest version string
     */
    String getVersion();
}
