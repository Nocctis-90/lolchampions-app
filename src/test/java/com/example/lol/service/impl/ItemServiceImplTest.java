package com.example.lol.service.impl;

import com.example.lol.model.Item;
import com.example.lol.model.ItemResponse;
import com.example.lol.service.DataDragonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private DataDragonService dataDragonService;

    private ItemServiceImpl itemService;

    @BeforeEach
    void setUp() {
        itemService = new ItemServiceImpl(restTemplate, dataDragonService);
    }

    @Test
    void getAllItems_shouldReturnListOfItems() {
        when(dataDragonService.getLatestVersion()).thenReturn("15.24.1");

        ItemResponse response = new ItemResponse();
        Map<String, Item> data = new HashMap<>();
        Item item = new Item();
        item.setName("Doran's Blade");
        data.put("1055", item);
        response.setData(data);

        when(restTemplate.getForObject(contains("item.json"), eq(ItemResponse.class))).thenReturn(response);

        List<Item> result = itemService.getAllItems();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Doran's Blade", result.get(0).getName());
    }

    @Test
    void getAllItems_shouldReturnEmptyListWhenNull() {
        when(dataDragonService.getLatestVersion()).thenReturn("15.24.1");
        when(restTemplate.getForObject(contains("item.json"), eq(ItemResponse.class))).thenReturn(null);

        List<Item> result = itemService.getAllItems();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllItems_shouldReturnEmptyListOnException() {
        when(dataDragonService.getLatestVersion()).thenReturn("15.24.1");
        when(restTemplate.getForObject(contains("item.json"), eq(ItemResponse.class)))
                .thenThrow(new RuntimeException("API Error"));

        List<Item> result = itemService.getAllItems();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
