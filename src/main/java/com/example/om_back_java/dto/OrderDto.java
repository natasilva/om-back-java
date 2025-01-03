package com.example.om_back_java.dto;

import com.example.om_back_java.entities.OrderAdditional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String code;
    private String description;
    private String name;
    private float value;
    private String phone;
    private LocalDateTime orderDate;
    private String district;
    private String number;
    private String street;
    private List<String> notes;
    private OrderDrinkDto[] orderDrinks;
    private OrderAdditionalDto[] orderAdditionals;
    private OrderBurgerDto[] orderBurgers;
}
