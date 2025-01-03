package com.example.om_back_java.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderAdditionalDto {
    private Long ingredientId;
    private Integer quantity;
}
