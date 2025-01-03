package com.example.om_back_java.services;

import com.example.om_back_java.dto.OrderAdditionalDto;
import com.example.om_back_java.dto.OrderBurgerDto;
import com.example.om_back_java.dto.OrderDrinkDto;
import com.example.om_back_java.dto.OrderDto;
import com.example.om_back_java.entities.*;
import com.example.om_back_java.entities.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
public class OrderService {
    @Autowired
    private BurgerService burgerService;

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private IngredientService ingredientService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Order create(OrderDto orderDto) {
        if (orderDto == null || (orderDto.getOrderDrinks() == null || orderDto.getOrderBurgers() == null || orderDto.getOrderAdditionals() == null)) {
            throw new IllegalArgumentException("OrderDto is invalid");
        }

        Order order = new Order();
        order.setCode(orderDto.getCode());
        order.setDescription(orderDto.getDescription());
        order.setName(orderDto.getName());
        order.setValue(orderDto.getValue());
        order.setPhone(orderDto.getPhone());
        order.setOrderDate(orderDto.getOrderDate());

        List<String> notes = orderDto.getNotes();
        order.setNotes(notes);

        Address address = new Address();
        address.setDistrict(orderDto.getDistrict());
        address.setNumber(orderDto.getNumber());
        address.setStreet(orderDto.getStreet());
        order.setAddress(address);

        Set<Long> drinksId = Arrays.stream(orderDto.getOrderDrinks()).map(OrderDrinkDto::getDrinkId).collect(Collectors.toSet());
        Set<Long> burgersId = Arrays.stream(orderDto.getOrderBurgers()).map(OrderBurgerDto::getBurgerId).collect(Collectors.toSet());
        Set<Long> additionalsId = Arrays.stream(orderDto.getOrderAdditionals()).map(OrderAdditionalDto::getIngredientId).collect(Collectors.toSet());

        Map<Long, Drink> drinks = this.drinkService.findAll(drinksId).stream().collect(Collectors.toMap(Drink::getId, Function.identity()));
        Map<Long, Burger> burgers = this.burgerService.findAll(burgersId).stream().collect(Collectors.toMap(Burger::getId, Function.identity()));
        Map<Long, Ingredient> additionals = this.ingredientService.findAll(null, additionalsId).stream().collect(Collectors.toMap(Ingredient::getId, Function.identity()));

        Set<OrderDrink> orderDrinks = Arrays.stream(orderDto.getOrderDrinks())
                .map(orderDrinkDto -> createOrderDrink(orderDrinkDto, order, drinks))
                .collect(Collectors.toSet());

        Set<OrderBurger> orderBurgers = Arrays.stream(orderDto.getOrderBurgers())
                .map(orderBurgerDto -> createOrderBurger(orderBurgerDto, order, burgers))
                .collect(Collectors.toSet());

        Set<OrderAdditional> orderAdditionals = Arrays.stream(orderDto.getOrderAdditionals())
                .map(createOrderAdditional -> createOrderAdditional(createOrderAdditional, order, additionals))
                .collect(Collectors.toSet());

        order.setOrderDrinks(orderDrinks);
        order.setOrderBurgers(orderBurgers);
        order.setOrderAdditionals(orderAdditionals);

        this.entityManager.persist(order);
        return order;
    }

    public List<Order> findAll() {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        return entityManager.createQuery(criteriaQuery.select(root)).getResultList();
    }

    public OrderDrink createOrderDrink(OrderDrinkDto orderDrinkDto, Order order, Map<Long, Drink> drinks) {
        Drink drink = drinks.get(orderDrinkDto.getDrinkId());
        return new OrderDrink(null, orderDrinkDto.getQuantity(), drink, order);
    }

    public OrderBurger createOrderBurger(OrderBurgerDto orderBurgerDto, Order order, Map<Long, Burger> burgers) {
        Burger burger = burgers.get(orderBurgerDto.getBurgerId());
        return new OrderBurger(null, orderBurgerDto.getQuantity(), burger, order);
    }

    public OrderAdditional createOrderAdditional(OrderAdditionalDto orderAdditionalDto, Order order, Map<Long, Ingredient> additionals) {
        Ingredient additional = additionals.get(orderAdditionalDto.getIngredientId());
        return new OrderAdditional(null, orderAdditionalDto.getQuantity(), additional, order);
    }
}
