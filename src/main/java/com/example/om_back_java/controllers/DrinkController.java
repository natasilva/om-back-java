package com.example.om_back_java.controllers;

import com.example.om_back_java.entities.Drink;
import com.example.om_back_java.services.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/drinks")
public class DrinkController {
    @Autowired
    private DrinkService drinkService;

    @GetMapping
    public ResponseEntity<List<Drink>> findAll() {
        List<Drink> drinks = this.drinkService.findAll();
        return new ResponseEntity<>(drinks, HttpStatus.OK);
    }
}
