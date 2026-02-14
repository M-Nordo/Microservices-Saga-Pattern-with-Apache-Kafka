package com.example.orderservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.event.OrderEvent;
import com.example.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Order createOrder(Order order) {
        order.setStatus("PENDING");
        Order savedOrder = orderRepository.save(order);
        kafkaTemplate.send("order-topic", new OrderEvent(savedOrder.getId(), savedOrder.getProductId(), savedOrder.getQuantity(), "PENDING"));
        return savedOrder;
    }

    @KafkaListener(topics = "stock-topic", groupId = "order-group")
    public void handleStockResponse(OrderEvent event) {
        System.out.println("KAFKA'DAN CEVAP GELDİ! Durum: " + event.getStatus());
        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
            order.setStatus(event.getStatus());
            orderRepository.save(order);
        });
    }
}