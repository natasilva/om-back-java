package com.example.om_back_java.controllers;

import com.example.om_back_java.dto.IngredientDto;
import com.example.om_back_java.entities.Ingredient;
import com.example.om_back_java.entities.Ingredient;
import com.example.om_back_java.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<Ingredient> create(@RequestBody IngredientDto ingredientDTO) {
        Ingredient ingredient = this.ingredientService.create(ingredientDTO);
        return new ResponseEntity<>(ingredient, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> findAll(@RequestParam(value = "isAdditional", required = false) Boolean isAdditional) {
        List<Ingredient> ingredients = this.ingredientService.findAll(isAdditional, new HashSet<>(), true);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }
}
