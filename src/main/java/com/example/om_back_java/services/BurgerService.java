package com.example.om_back_java.services;

import com.example.om_back_java.entities.Burger;
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
public class BurgerService {
    @PersistenceContext
    EntityManager entityManager;

    public List<Burger> findAll() {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Burger> criteriaQuery = criteriaBuilder.createQuery(Burger.class);
        Root<Burger> root = criteriaQuery.from(Burger.class);

        return entityManager.createQuery(criteriaQuery.select(root)).getResultList();
    }
}
