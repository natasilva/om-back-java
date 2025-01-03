package com.example.om_back_java.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    @Column()
    private String description;

    @Column()
    private float unit_price;

    @Column()
    private Boolean is_additional;

    public void setUnitPrice(float unitPrice) {
        this.unit_price = unitPrice;
    }

    public void setIsAdditional(boolean isAdditional) {
        this.is_additional = isAdditional;
    }
}

