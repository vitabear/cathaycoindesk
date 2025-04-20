package com.example.cathaycoindesk.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/coindesk")
public class CoindeskController {

    @Operation(summary = "取得原始 coindesk API 回傳內容")
    @GetMapping("/origin")
    public ResponseEntity<Object> getOriginalCoindesk() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
            return ResponseEntity.ok(restTemplate.getForObject(url, Object.class));
        } catch (Exception e) {
            // fallback mock data
            Map<String, Object> mock = new HashMap<>();

            mock.put("time", Map.of(
                    "updated", "Aug 3, 2022 20:25:00 UTC",
                    "updatedISO", "2022-08-03T20:25:00+00:00",
                    "updateduk", "Aug 3, 2022 at 21:25 BST"
            ));
            mock.put("disclaimer", "This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org");
            mock.put("chartName", "Bitcoin");
            mock.put("bpi", Map.of(
                    "USD", Map.of("code", "USD", "symbol", "$", "rate", "23,342.0112", "description", "US Dollar", "rate_float", 23342.0112),
                    "GBP", Map.of("code", "GBP", "symbol", "£", "rate", "19,504.3978", "description", "British Pound Sterling", "rate_float", 19504.3978),
                    "EUR", Map.of("code", "EUR", "symbol", "€", "rate", "22,738.5269", "description", "Euro", "rate_float", 22738.5269)
            ));

            return ResponseEntity.ok(mock);
        }
    }
}
