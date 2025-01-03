package com.example.om_back_java.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDrinkDto {
    private Long drinkId;
    private Integer quantity;
}
