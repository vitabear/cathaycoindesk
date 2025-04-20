package com.example.cathaycoindesk.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CoindeskController.class)
public class CoindeskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate; // if RestTemplate is in a @Bean, otherwise not needed

    @Test
    public void testGetOriginalCoindesk_withMockFallback() throws Exception {
        // 模擬 coindesk API 無法使用的情況下會進入 catch 區塊，回傳 mock 資料

        mockMvc.perform(MockMvcRequestBuilders.get("/coindesk/origin")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.time.updated").value("Aug 3, 2022 20:25:00 UTC"))
                .andExpect(jsonPath("$.bpi.USD.code").value("USD"))
                .andExpect(jsonPath("$.bpi.GBP.rate").value("19,504.3978"))
                .andExpect(jsonPath("$.bpi.EUR.symbol").value("€"));
    }
}
