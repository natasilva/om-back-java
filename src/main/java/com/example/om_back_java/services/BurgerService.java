package com.example.om_back_java.services;

import com.example.om_back_java.dto.BurgerDto;
import com.example.om_back_java.dto.BurgerIngredientDto;
import com.example.om_back_java.entities.*;
import com.example.om_back_java.entities.Burger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
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
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
public class BurgerService {
    @Autowired
    private IngredientService ingredientService;

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public Burger create(BurgerDto burgerDto) {
        if (burgerDto == null || burgerDto.getBurgerIngredients() == null || burgerDto.getBurgerIngredients().length == 0) {
            throw new IllegalArgumentException("BurgerDto is invalid");
        }

        Burger burger = new Burger();
        burger.setCode(burgerDto.getCode());
        burger.setDescription(burgerDto.getDescription());
        burger.setUnitPrice(burgerDto.getUnitPrice());

        BurgerIngredientDto[] burgerIngredientsDto = burgerDto.getBurgerIngredients();

        Set<BurgerIngredient> burgerIngredients = Arrays.stream(burgerIngredientsDto)
                .map(burgerIngredientDto -> {
                    Ingredient ingredient = this.ingredientService.findById(burgerIngredientDto.getIngredientId());
                    if (ingredient == null)
                        throw new EntityNotFoundException("Ingredient not found: " + burgerIngredientDto.getIngredientId());
                    return new BurgerIngredient(null, burgerIngredientDto.getQuantity(), ingredient);
                })
                .collect(Collectors.toSet());

        burger.setBurgerIngredients(burgerIngredients);

        this.entityManager.persist(burger);
        return burger;
    }

    public List<Burger> findAll() {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Burger> criteriaQuery = criteriaBuilder.createQuery(Burger.class);
        Root<Burger> root = criteriaQuery.from(Burger.class);

        return entityManager.createQuery(criteriaQuery.select(root)).getResultList();
    }
}
