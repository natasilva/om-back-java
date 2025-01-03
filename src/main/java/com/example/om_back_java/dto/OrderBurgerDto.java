package com.example.om_back_java.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderBurgerDto {
    private Long burgerId;
    private Integer quantity;
}
