package com.example.om_back_java.controllers;

import com.example.om_back_java.dto.BurgerDto;
import com.example.om_back_java.entities.Burger;
import com.example.om_back_java.services.BurgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/burgers")
public class BurgerController {
    @Autowired
    private BurgerService burgerService;

    @PostMapping
    public ResponseEntity<Burger> create(@RequestBody BurgerDto burgerDTO) {
        Burger burger = burgerService.create(burgerDTO);
        return new ResponseEntity<>(burger, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Burger>> findAll() {
        List<Burger> burgers = this.burgerService.findAll();
        return new ResponseEntity<>(burgers, HttpStatus.OK);
    }
}
