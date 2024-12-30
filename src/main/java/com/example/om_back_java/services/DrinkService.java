package com.example.om_back_java.services;

import com.example.om_back_java.entities.Drink;
import com.example.om_back_java.repositories.DrinkRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@NoArgsConstructor
@Service
public class DrinkService {
    @PersistenceContext
    EntityManager entityManager;

    public List<Drink> findAll() {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Drink> criteriaQuery = criteriaBuilder.createQuery(Drink.class);
        Root<Drink> root = criteriaQuery.from(Drink.class);

        return entityManager.createQuery(criteriaQuery.select(root)).getResultList();
    }
}
