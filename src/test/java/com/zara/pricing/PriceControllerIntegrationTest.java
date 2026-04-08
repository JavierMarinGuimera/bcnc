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
    @DisplayName("Return price list 1 for product 35455 on 2020-06-14 10:00")
    void shouldReturnPriceList1ForJune14At10() throws Exception {
        mockMvc.perform(get(URL)
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.finalPrice").value(35.50));
    }

    @Test
    @DisplayName("Return price list 2 for product 35455 on 2020-06-14 16:00")
    void shouldReturnPriceList2ForJune14At16() throws Exception {
        mockMvc.perform(get(URL)
                .param("applicationDate", "2020-06-14T16:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.finalPrice").value(25.45));
    }

    @Test
    @DisplayName("Return price list 1 for product 35455 on 2020-06-14 21:00")
    void shouldReturnPriceList1ForJune14At21() throws Exception {
        mockMvc.perform(get(URL)
                .param("applicationDate", "2020-06-14T21:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.finalPrice").value(35.50));
    }

    @Test
    @DisplayName("Return price list 3 for product 35455 on 2020-06-15 10:00")
    void shouldReturnPriceList3ForJune15At10() throws Exception {
        mockMvc.perform(get(URL)
                .param("applicationDate", "2020-06-15T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.finalPrice").value(30.50));
    }

    @Test
    @DisplayName("Return price list 4 for product 35455 on 2020-06-16 21:00")
    void shouldReturnPriceList4ForJune16At21() throws Exception {
        mockMvc.perform(get(URL)
                .param("applicationDate", "2020-06-16T21:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.finalPrice").value(38.95));
    }

    @Test
    @DisplayName("Return all prices")
    void shouldReturnAllPrices() throws Exception {
        mockMvc.perform(get("/prices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].productId").exists())
                .andExpect(jsonPath("$[0].brandId").exists());
    }

    @Test
    @DisplayName("Return 404 when price is not found")
    void shouldReturn404WhenPriceNotFound() throws Exception {
        mockMvc.perform(get(URL)
                .param("applicationDate", "2020-06-14T10:00:00")
                .param("productId", "99999")
                .param("brandId", "99"))
                .andExpect(status().isNotFound());
    }
}
