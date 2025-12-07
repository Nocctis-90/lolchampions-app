package com.example.lol.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataDragonServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    private DataDragonServiceImpl dataDragonService;

    @BeforeEach
    void setUp() {
        dataDragonService = new DataDragonServiceImpl(restTemplate);
    }

    @Test
    void getLatestVersion_shouldReturnFirstVersion() {
        String[] versions = { "15.24.1", "15.23.1", "15.22.1" };
        when(restTemplate.getForObject(anyString(), eq(String[].class))).thenReturn(versions);

        String result = dataDragonService.getLatestVersion();

        assertEquals("15.24.1", result);
    }

    @Test
    void getLatestVersion_shouldReturnFallbackWhenNull() {
        when(restTemplate.getForObject(anyString(), eq(String[].class))).thenReturn(null);

        String result = dataDragonService.getLatestVersion();

        assertEquals("15.24.1", result);
    }

    @Test
    void getLatestVersion_shouldReturnFallbackWhenEmpty() {
        when(restTemplate.getForObject(anyString(), eq(String[].class))).thenReturn(new String[] {});

        String result = dataDragonService.getLatestVersion();

        assertEquals("15.24.1", result);
    }

    @Test
    void getLatestVersion_shouldReturnFallbackOnException() {
        when(restTemplate.getForObject(anyString(), eq(String[].class)))
                .thenThrow(new RuntimeException("API Error"));

        String result = dataDragonService.getLatestVersion();

        assertEquals("15.24.1", result);
    }

    @Test
    void getVersion_shouldReturnLatestVersion() {
        String[] versions = { "15.24.1" };
        when(restTemplate.getForObject(anyString(), eq(String[].class))).thenReturn(versions);

        String result = dataDragonService.getVersion();

        assertEquals("15.24.1", result);
    }
}
