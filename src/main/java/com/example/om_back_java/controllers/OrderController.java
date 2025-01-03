package com.example.om_back_java.controllers;

import com.example.om_back_java.dto.OrderDto;
import com.example.om_back_java.entities.Order;
import com.example.om_back_java.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderDto orderDto) {
        Order order = this.orderService.create(orderDto);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        List<Order> orders = this.orderService.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
