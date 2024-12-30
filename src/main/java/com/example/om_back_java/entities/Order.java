package com.example.om_back_java.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    @Column()
    private String description;

    @Column()
    private float value;

    @Column()
    private String name;

    @Column()
    private String phone;

    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(name = "orderDate")
    private LocalDateTime orderDate;

    @Column(name="notes")
    @ElementCollection
    private Set<String> notes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "drinkId")
    private Set<OrderDrink> orderDrinks = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ingredientId")
    private Set<OrderAdditional> orderAdditionals = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "burgerId")
    private Set<OrderBurger> orderBurgers = new HashSet<>();
}
