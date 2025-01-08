package com.example.om_back_java.controllers;

import com.example.om_back_java.dto.ListOrdersDto;
import com.example.om_back_java.dto.OrderDto;
import com.example.om_back_java.entities.Order;
import com.example.om_back_java.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        try {
            Order saved = this.orderService.create(order);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(@ModelAttribute ListOrdersDto listOrdersDto) {
        Map<String, Object> orders = this.orderService.findAll(listOrdersDto);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
