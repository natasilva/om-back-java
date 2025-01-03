package com.example.om_back_java.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDto {
    private String code;
    private String description;
    private float unitPrice;
    private Boolean isAdditional;
}
