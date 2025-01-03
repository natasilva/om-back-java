package com.example.om_back_java.dto;

import com.example.om_back_java.entities.BurgerIngredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BurgerDto {
    private String code;
    private String description;
    private float unitPrice;
    private BurgerIngredientDto[] burgerIngredients;
}
