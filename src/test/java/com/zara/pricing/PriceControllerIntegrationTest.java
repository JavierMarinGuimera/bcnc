package com.zara.pricing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String URL = "/prices/get-price";

    @Test
    @DisplayName("Test 1: 14-06 10:00")
    void test1() throws Exception {
        mockMvc.perform(get(URL)
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.finalPrice").value(35.50));
    }

    @Test
    @DisplayName("Test 2: 14-06 16:00")
    void test2() throws Exception {
        mockMvc.perform(get(URL)
                .param("applicationDate", "2020-06-14T16:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.finalPrice").value(25.45));
    }

    @Test
    @DisplayName("Test 3: 14-06 21:00")
    void test3() throws Exception {
        mockMvc.perform(get(URL)
                .param("applicationDate", "2020-06-14T21:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.finalPrice").value(35.50));
    }

    @Test
    @DisplayName("Test 4: 15-06 10:00")
    void test4() throws Exception {
        mockMvc.perform(get(URL)
                .param("applicationDate", "2020-06-15T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.finalPrice").value(30.50));
    }

    @Test
    @DisplayName("Test 5: 16-06 21:00")
    void test5() throws Exception {
        mockMvc.perform(get(URL)
                .param("applicationDate", "2020-06-16T21:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.finalPrice").value(38.95));
    }

    @Test
    @DisplayName("Get all prices")
    void getAllPrices() throws Exception {
        mockMvc.perform(get("/prices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").exists())
                .andExpect(jsonPath("$[0].brandId").exists());
    }
}
