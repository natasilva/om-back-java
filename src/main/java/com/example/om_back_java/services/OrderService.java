package com.example.om_back_java.services;

import com.example.om_back_java.dto.*;
import com.example.om_back_java.entities.Order;
import com.example.om_back_java.repositories.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor
@Service
public class OrderService {
    @Autowired
    private BurgerService burgerService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    public Order create(Order order) {
        return this.orderRepository.save(order);
    }

    public Map<String, Object> findAll(ListOrdersDto listOrdersDto) {
        Integer pageIndex = listOrdersDto.getPageIndex();
        Integer pageSize = listOrdersDto.getPageSize();

        String name = listOrdersDto.getName();
        LocalDateTime initialDate = listOrdersDto.getInitialDate();
        LocalDateTime endDate = listOrdersDto.getEndDate();

        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();

        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        Set<Predicate> predicates = new HashSet<>();

        if (name != null && !name.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (initialDate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("order_date"), initialDate));
        }
        if (endDate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("order_date"), endDate));
        }

        Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        List<Order> orders = entityManager.createQuery(criteriaQuery.select(root).where(finalPredicate))
                .setFirstResult((pageIndex - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        Long count = this.count(listOrdersDto, criteriaBuilder);

        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("rows", orders);

        return response;
    }

    private Long count(ListOrdersDto listOrdersDto, CriteriaBuilder criteriaBuilder) {
        String name = listOrdersDto.getName();
        LocalDateTime initialDate = listOrdersDto.getInitialDate();
        LocalDateTime endDate = listOrdersDto.getEndDate();

        Set<Predicate> predicates = new HashSet<>();


        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Order> root = countQuery.from(Order.class);

        if (name != null && !name.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (initialDate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("order_date"), initialDate));
        }

        if (endDate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("order_date"), endDate));
        }

        Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        countQuery.select(criteriaBuilder.count(root)).where(finalPredicate);

        return entityManager.createQuery(countQuery).getSingleResult();
    }

//    @Transactional
//    public Order create(OrderDto orderDto) {
//        if (orderDto == null || (orderDto.getOrderDrinks() == null || orderDto.getOrderBurgers() == null || orderDto.getOrderAdditionals() == null)) {
//            throw new IllegalArgumentException("OrderDto is invalid");
//        }
//
//        Order order = new Order();
//        order.setCode(orderDto.getCode());
//        order.setDescription(orderDto.getDescription());
//        order.setName(orderDto.getName());
//        order.setValue(orderDto.getValue());
//        order.setPhone(orderDto.getPhone());
//        order.setOrderDate(orderDto.getOrderDate());
//
//        List<String> notes = orderDto.getNotes();
//        order.setNotes(notes);
//
//        Address address = new Address();
//        address.setDistrict(orderDto.getDistrict());
//        address.setNumber(orderDto.getNumber());
//        address.setStreet(orderDto.getStreet());
//        order.setAddress(address);

//        Set<Long> drinksId = Arrays.stream(orderDto.getOrderDrinks()).map(OrderDrinkDto::getDrinkId).collect(Collectors.toSet());
//        Set<Long> burgersId = Arrays.stream(orderDto.getOrderBurgers()).map(OrderBurgerDto::getBurgerId).collect(Collectors.toSet());
//        Set<Long> additionalsId = Arrays.stream(orderDto.getOrderAdditionals()).map(OrderAdditionalDto::getIngredientId).collect(Collectors.toSet());

//        Map<Long, Drink> drinks = this.drinkService.findAll(drinksId).stream().collect(Collectors.toMap(Drink::getId, Function.identity()));
//        Map<Long, Burger> burgers = this.burgerService.findAll(burgersId).stream().collect(Collectors.toMap(Burger::getId, Function.identity()));
//        Map<Long, Ingredient> additionals = this.ingredientService.findAll(null, additionalsId, false).stream().collect(Collectors.toMap(Ingredient::getId, Function.identity()));

//        if (drinks.size() != drinksId.size()) {
//            throw new EntityNotFoundException("One or more drinks not found");
//        }
//        if (burgers.size() != burgersId.size()) {
//            throw new EntityNotFoundException("One or more burgers not found");
//        }
//        if (additionals.size() != additionalsId.size()) {
//            throw new EntityNotFoundException("One or more additionals not found");
//        }

//        Set<OrderDrink> orderDrinks = Arrays.stream(orderDto.getOrderDrinks())
//                .map(orderDrinkDto -> createOrderDrink(orderDrinkDto, drinks))
//                .collect(Collectors.toSet());

//        Set<OrderBurger> orderBurgers = Arrays.stream(orderDto.getOrderBurgers())
//                .map(orderBurgerDto -> createOrderBurger(orderBurgerDto, burgers))
//                .collect(Collectors.toSet());
//
//        Set<OrderAdditional> orderAdditionals = Arrays.stream(orderDto.getOrderAdditionals())
//                .map(createOrderAdditionalDto -> createOrderAdditional(createOrderAdditionalDto, additionals))
//                .collect(Collectors.toSet());

//        order.setOrderDrinks(orderDrinks);
//        order.setOrderBurgers(orderBurgers);
//        order.setOrderAdditionals(orderAdditionals);

//        return this.orderRepository.save(order);
//    }

//    public OrderDrink createOrderDrink(OrderDrinkDto orderDrinkDto, Map<Long, Drink> drinks) {
//        Drink drink = drinks.get(orderDrinkDto.getDrinkId());
//        return new OrderDrink(null, orderDrinkDto.getQuantity(), drink);
//    }
//
//    public OrderBurger createOrderBurger(OrderBurgerDto orderBurgerDto) {
//        Burger burger = this.burgerService.findById(orderBurgerDto.getBurgerId());
//        return new OrderBurger(null, orderBurgerDto.getQuantity(), burger);
//    }
//
//    public OrderAdditional createOrderAdditional(OrderAdditionalDto orderAdditionalDto, Map<Long, Ingredient> additionals) {
//        Ingredient additional = additionals.get(orderAdditionalDto.getIngredientId());
//        return new OrderAdditional(null, orderAdditionalDto.getQuantity(), additional);
//    }
}
