package com.example.om_back_java.services;

import com.example.om_back_java.dto.DrinkDto;
import com.example.om_back_java.entities.Drink;
import com.example.om_back_java.entities.Ingredient;
import com.example.om_back_java.repositories.DrinkRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Service
public class DrinkService {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public Drink create(DrinkDto drinkDto) {
        Drink drink = new Drink();

        drink.setHasSugar(drinkDto.getHasSugar());
        drink.setCode(drinkDto.getCode());
        drink.setUnitPrice(drinkDto.getUnitPrice());
        drink.setDescription(drinkDto.getDescription());

        this.entityManager.persist(drink);
        return drink;
    }

    public List<Drink> findAll(Set<Long> drinkIds) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Drink> criteriaQuery = criteriaBuilder.createQuery(Drink.class);
        Root<Drink> root = criteriaQuery.from(Drink.class);
        CriteriaQuery<Drink> finalQuery = criteriaQuery.select(root).where(!drinkIds.isEmpty() ? root.get("id").in(drinkIds) : criteriaBuilder.conjunction());

        return entityManager.createQuery(finalQuery).getResultList();
    }

    public Drink findById(Integer id) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Drink> criteriaQuery = criteriaBuilder.createQuery(Drink.class);
        Root<Drink> root = criteriaQuery.from(Drink.class);

        Predicate predicate = criteriaBuilder.equal(root.get("id"), id);
        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}
