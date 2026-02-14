package com.example.stockservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.stockservice.entity.Stock;
import com.example.stockservice.event.OrderEvent;
import com.example.stockservice.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "order-topic", groupId = "stock-group")
    public void processOrder(OrderEvent event) {
        Stock stock = stockRepository.findByProductId(event.getProductId())
                .orElseThrow(() -> new RuntimeException("Stok bulunamadı!"));

        if (stock.getAvailableQuantity() >= event.getQuantity()) {
            stock.setAvailableQuantity(stock.getAvailableQuantity() - event.getQuantity());
            stockRepository.save(stock);
            event.setStatus("COMPLETED");
        } else {
            event.setStatus("CANCELLED");
        }
        kafkaTemplate.send("stock-topic", event);
    }
}