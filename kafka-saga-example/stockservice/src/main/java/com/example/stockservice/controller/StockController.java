package com.example.stockservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stockservice.entity.Stock;
import com.example.stockservice.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockRepository stockRepository;

    // Mevcut stokları görmek için
    @GetMapping
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    // Manuel stok eklemek veya güncellemek için (Test kolaylığı sağlar)
    @PostMapping
    public Stock updateStock(@RequestBody Stock stock) {
        return stockRepository.save(stock);
    }
}