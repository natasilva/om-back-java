package com.example.om_back_java.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_burger")
public class OrderBurger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "burgerId")
    private Burger burger;

//    @ManyToOne
//    @JoinColumn(name = "orderId")
//    private Order order;
}
