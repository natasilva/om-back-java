package com.example.om_back_java.services;

import com.example.om_back_java.dto.IngredientDto;
import com.example.om_back_java.entities.Drink;
import com.example.om_back_java.entities.Ingredient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Service
public class IngredientService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Ingredient create(IngredientDto ingredientDto) {
        Ingredient ingredient = new Ingredient();

        ingredient.setIsAdditional(ingredientDto.getIsAdditional());
        ingredient.setCode(ingredientDto.getCode());
        ingredient.setUnitPrice(ingredientDto.getUnitPrice());
        ingredient.setDescription(ingredientDto.getDescription());

        this.entityManager.persist(ingredient);
        return ingredient;
    }

    public List<Ingredient> findAll(Boolean isAdditional, Set<Long> ingredientIds, Boolean findAll) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Ingredient> criteriaQuery = criteriaBuilder.createQuery(Ingredient.class);

        Root<Ingredient> root = criteriaQuery.from(Ingredient.class);

        Predicate predicate = isAdditional != null ? criteriaBuilder.equal(root.get("is_additional"), isAdditional) : criteriaBuilder.conjunction();
        Predicate predicate2 = !findAll ? root.get("id").in(ingredientIds) : criteriaBuilder.conjunction();

        criteriaQuery.where(predicate, predicate2);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Ingredient findById(Integer id) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Ingredient> criteriaQuery = criteriaBuilder.createQuery(Ingredient.class);
        Root<Ingredient> root = criteriaQuery.from(Ingredient.class);

        Predicate predicate = criteriaBuilder.equal(root.get("id"), id);
        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}
