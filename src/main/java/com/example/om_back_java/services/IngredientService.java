package com.example.om_back_java.services;

import com.example.om_back_java.entities.Ingredient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@NoArgsConstructor
@Service
public class IngredientService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Ingredient> findAll(Boolean isAdditional) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Ingredient> criteriaQuery = criteriaBuilder.createQuery(Ingredient.class);

        Root<Ingredient> root = criteriaQuery.from(Ingredient.class);

        Predicate predicate = isAdditional != null ? criteriaBuilder.equal(root.get("is_additional"), isAdditional) : criteriaBuilder.conjunction();

        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
