package com.example.spring_la_mia_pizzeria_crud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.spring_la_mia_pizzeria_crud.models.Pizza;
import com.example.spring_la_mia_pizzeria_crud.repositories.PizzaRepository;

@Controller
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping("/")
    public String index(Model model) {
        // Recupera tutte le pizze dal database tramite il repository
        List<Pizza> pizze = pizzaRepository.findAll();

        // Aggiunge l'elenco delle pizze al modello per essere visualizzato nella vista
        model.addAttribute("pizze", pizze);

        // Restituisce il nome della vista che sarà renderizzata (index.html)
        return "index";
    }
}

