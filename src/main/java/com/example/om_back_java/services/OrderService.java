package com.example.om_back_java.services;

import com.example.om_back_java.entities.Order;
import com.example.om_back_java.entities.Order;
import com.example.om_back_java.repositories.OrderRepository;
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
public class OrderService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Order> findAll() {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        return entityManager.createQuery(criteriaQuery.select(root)).getResultList();
    }
}
