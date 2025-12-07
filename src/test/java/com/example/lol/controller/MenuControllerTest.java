package com.example.lol.controller;

import com.example.lol.model.Item;
import com.example.lol.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MenuController.class)
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    void getItems_shouldReturnListOfItems() throws Exception {
        Item item = new Item();
        item.setName("Doran's Blade");
        item.setDescription("A basic starting item.");

        when(itemService.getAllItems()).thenReturn(Arrays.asList(item));

        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Doran's Blade"))
                .andExpect(jsonPath("$[0].description").value("A basic starting item."));
    }

    @Test
    void getItems_shouldReturnEmptyList() throws Exception {
        when(itemService.getAllItems()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
