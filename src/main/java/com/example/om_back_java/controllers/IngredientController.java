package com.example.om_back_java.controllers;

import com.example.om_back_java.entities.Ingredient;
import com.example.om_back_java.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<List<Ingredient>> findAll(@RequestParam(value = "isAdditional", required = false) Boolean isAdditional) {
        List<Ingredient> ingredients = this.ingredientService.findAll(isAdditional);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }
}
