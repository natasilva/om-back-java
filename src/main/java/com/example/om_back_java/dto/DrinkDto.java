package com.example.om_back_java.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DrinkDto {
    private String code;
    private String description;
    private float unitPrice;
    private boolean hasSugar;
}
