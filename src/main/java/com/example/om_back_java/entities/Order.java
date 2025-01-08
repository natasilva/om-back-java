package com.example.om_back_java.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "\"order\"")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    @Column
    private String description;

    @Column
    private float value;

    @Column
    private String name;

    @Column
    private String phone;

    @Embedded
    private Address address;

//    @CreationTimestamp
    @Column(name = "order_date")
    private LocalDateTime order_date;

    @ElementCollection
    @CollectionTable(name = "order_notes", joinColumns = @JoinColumn(name = "orderId"))
    @Column(name = "notes")
    private List<String> notes = new ArrayList<>();

    // Relação bidirecional com OrderDrink
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Set<OrderDrink> orderDrinks = new HashSet<>();

    // Relação bidirecional com OrderAdditional
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Set<OrderAdditional> orderAdditionals = new HashSet<>();

    // Relação bidirecional com OrderBurger
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Set<OrderBurger> orderBurgers = new HashSet<>();
}
