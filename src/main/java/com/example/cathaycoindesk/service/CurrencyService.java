
package com.example.cathaycoindesk.service;

import com.example.cathaycoindesk.entity.Currency;
import com.example.cathaycoindesk.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository repository;

    public List<Currency> getAll() {
        return repository.findAll()
                .stream()
                .sorted((a, b) -> a.getCode().compareTo(b.getCode()))
                .toList();
    }

    public Optional<Currency> getByCode(String code) {
        return repository.findById(code);
    }

    public Currency create(Currency currency) {
        return repository.save(currency);
    }

    public Currency update(String code, Currency currency) {
        currency.setCode(code);
        return repository.save(currency);
    }

    public void delete(String code) {
        repository.deleteById(code);
    }
}
