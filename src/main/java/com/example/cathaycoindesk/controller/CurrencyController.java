package com.example.cathaycoindesk.controller;

import com.example.cathaycoindesk.entity.Currency;
import com.example.cathaycoindesk.repository.CurrencyRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Operation(summary = "取得所有幣別", description = "依幣別代碼排序")
    @GetMapping
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll().stream()
                .sorted(Comparator.comparing(Currency::getCode))
                .toList();
    }

    @Operation(summary = "查詢特定幣別")
    @GetMapping("/{code}")
    public Currency getCurrencyByCode(
            @Parameter(description = "幣別代碼，例如：USD", required = true)
            @PathVariable String code) {
        return currencyRepository.findById(code).orElse(null);
    }

    @Operation(
            summary = "新增幣別",
            requestBody = @RequestBody(
                    description = "幣別資訊",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"code\":\"JPY\",\"nameZh\":\"日圓\",\"description\":\"Japanese Yen\",\"symbol\":\"¥\"}"
                            )
                    )
            )
    )
    @PostMapping
    public Currency createCurrency(@org.springframework.web.bind.annotation.RequestBody Currency currency) {
        return currencyRepository.save(currency);
    }

    @Operation(
            summary = "更新幣別",
            requestBody = @RequestBody(
                    description = "要更新的幣別內容（code 與 path 中一致）",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"code\":\"USD\",\"nameZh\":\"美金\",\"description\":\"United States Dollar\",\"symbol\":\"$\"}"
                            )
                    )
            )
    )
    @PutMapping("/{code}")
    public Currency updateCurrency(
            @Parameter(description = "幣別代碼，例如：USD", required = true)
            @PathVariable String code,
            @org.springframework.web.bind.annotation.RequestBody Currency currency) {
        if (!currencyRepository.existsById(code)) {
            return null;
        }
        return currencyRepository.save(currency);
    }

    @Operation(summary = "刪除幣別")
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCurrency(
            @Parameter(description = "幣別代碼，例如：GBP", required = true)
            @PathVariable String code) {
        currencyRepository.deleteById(code);
        return ResponseEntity.noContent().build();
    }
}
