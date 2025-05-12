package com.example.spring_la_mia_pizzeria_crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_la_mia_pizzeria_crud.models.Pizza;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    
}

