package com.example.spring_la_mia_pizzeria_crud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.spring_la_mia_pizzeria_crud.models.Ingrediente;
import com.example.spring_la_mia_pizzeria_crud.models.Pizza;
import com.example.spring_la_mia_pizzeria_crud.repositories.IngredienteRepository;
import com.example.spring_la_mia_pizzeria_crud.repositories.PizzaRepository;
@Controller
@RequestMapping("/")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    // Metodo per visualizzare la lista delle pizze
    @GetMapping("/")
    public String index(Model model) {
        List<Pizza> pizze = pizzaRepository.findAll();
        model.addAttribute("pizze", pizze);
        return "index";  // Restituisce la vista index.html
    }

    // Metodo per visualizzare il form per creare una nuova pizza
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredienti", ingredienteRepository.findAll());
        return "create-pizza";  // Restituisce la vista create-pizza.html
    }

    // Metodo per salvare una pizza
    @PostMapping("/")
    public String savePizza(@ModelAttribute Pizza pizza, @RequestParam List<Long> ingredienti,
                            @RequestParam("fotoUrl") String fotoUrl, Model model) {
        // Imposta l'URL dell'immagine per la pizza
        pizza.setFotoUrl(fotoUrl);  // Salva direttamente l'URL nel database

        // Calcola il prezzo in base agli ingredienti
        double totalPrice = 0;
        for (Long id : ingredienti) {
            Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingrediente non trovato"));
            totalPrice += ingrediente.getPrezzo();  // Somma il prezzo degli ingredienti
        }

        pizza.setPrezzo(totalPrice);  // Imposta il prezzo totale della pizza
        pizzaRepository.save(pizza);  // Salva la pizza nel database

        return "redirect:/";  // Reindirizza alla lista delle pizze
    }
}



