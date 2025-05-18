package com.example.spring_la_mia_pizzeria_crud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.spring_la_mia_pizzeria_crud.models.Ingrediente;
import com.example.spring_la_mia_pizzeria_crud.models.Pizza;
import com.example.spring_la_mia_pizzeria_crud.repositories.IngredienteRepository;
import com.example.spring_la_mia_pizzeria_crud.repositories.PizzaRepository;

import jakarta.validation.Valid;
@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    // Metodo per visualizzare la lista delle pizze
    @GetMapping
    public String indexPizze(Model model) {
        List<Pizza> pizze = pizzaRepository.findAll();
        model.addAttribute("pizze", pizze);
        return "pizza/index";  // Restituisce la vista index.html
    }

    // Metodo per visualizzare il dettaglio di una pizza
    @GetMapping("/{id}")
    public String showPizzaDetail(@PathVariable Long id, Model model) {
        Pizza pizza = pizzaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pizza not found"));
        model.addAttribute("pizza", pizza);  // Aggiungi la pizza al modello
        return "pizza/details";  // La vista di dettaglio
    }


    // Metodo per visualizzare il form per creare una nuova pizza
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredienti", ingredienteRepository.findAll());
        return "pizza/create-pizza";  // Restituisce la vista create-pizza.html
    }

    // Metodo per salvare una pizza
    @PostMapping
    public String savePizza(@Valid @ModelAttribute Pizza pizza, BindingResult result, @RequestParam (value = "ingredienti", required = false) List<Long> ingredienti, Model model) {
         
    
        // Se ci sono errori di validazione, torna al form
        if (result.hasErrors()) {
            model.addAttribute("ingredienti", ingredienteRepository.findAll());
            return "pizza/create-pizza";  // Ritorna al form con gli errori
        }
        // Se nessun ingrediente è selezionato, restituisci un errore
        if (ingredienti == null || ingredienti.isEmpty()) {
            result.rejectValue("ingredienti", "ingredienti.required", "Devi selezionare almeno un ingrediente.");
            model.addAttribute("ingredienti", ingredienteRepository.findAll());
            return "pizza/create-pizza";  // Torna al form con l'errore
        }

         // Recupera gli ingredienti dal database tramite gli ID selezionati
        List<Ingrediente> ingredientiSelezionati = ingredienteRepository.findAllById(ingredienti);
        pizza.setIngredienti(ingredientiSelezionati);  // Associa gli ingredienti alla pizza


        // Calcola il prezzo in base agli ingredienti selezionati
        double totalPrice = 0;
        for (Long id : ingredienti) {
            Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingrediente non trovato"));
            totalPrice += ingrediente.getPrezzo();  // Somma il prezzo degli ingredienti
        }

        pizza.setPrezzo(totalPrice);  // Imposta il prezzo totale della pizza
        pizzaRepository.save(pizza);  // Salva la pizza nel database

        return "redirect:/pizzas";  // Reindirizza alla lista delle pizze
    }

     // Metodo per visualizzare il form per creare una nuova pizza

    @GetMapping("/edit/{id}")
public String showEditForm(@PathVariable Long id, Model model) {
    // Recupera la pizza dal database utilizzando il suo ID
    Pizza pizza = pizzaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Pizza non trovata con ID: " + id));
    
    // Aggiungi la pizza al modello per popolare il form
    model.addAttribute("pizza", pizza);
    
    // Aggiungi tutti gli ingredienti al modello per la selezione
    model.addAttribute("ingredienti", ingredienteRepository.findAll());
    
    // Restituisci la vista del form di modifica
    return "pizza/edit-pizza";
}


    // Metodo per aggiornare una pizza
    @PostMapping("/edit/{id}")
    public String updatePizza(@PathVariable Long id, 
                              @Valid @ModelAttribute Pizza pizza, 
                              BindingResult result, 
                              @RequestParam List<Long> ingredienti, 
                              Model model) {

        // Se ci sono errori di validazione, ritorna al form di modifica
        if (result.hasErrors()) {
            model.addAttribute("ingredienti", ingredienteRepository.findAll());
            return "pizza/create-pizza";  // Ritorna al form con gli errori
        }

        // Trova la pizza da aggiornare
        Pizza pizzaDaAggiornare = pizzaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Pizza non trovata"));

        // Aggiorna le informazioni della pizza
        pizzaDaAggiornare.setNome(pizza.getNome());
        pizzaDaAggiornare.setDescrizione(pizza.getDescrizione());
        pizzaDaAggiornare.setFotoUrl(pizza.getFotoUrl());

        // Trova gli ingredienti selezionati dalla form
        List<Ingrediente> ingredientiSelezionati = ingredienteRepository.findAllById(ingredienti);
        pizzaDaAggiornare.setIngredienti(ingredientiSelezionati);

        // Calcola il prezzo basato sugli ingredienti
        double totalPrice = 0;
        for (Ingrediente ingrediente : ingredientiSelezionati) {
            totalPrice += ingrediente.getPrezzo();
        }
        pizzaDaAggiornare.setPrezzo(totalPrice);

        // Salva la pizza aggiornata
        pizzaRepository.save(pizzaDaAggiornare);

        return "redirect:/pizzas";  // Ritorna alla lista delle pizze
    }

     // Metodo per eliminare una pizza
    @PostMapping("/delete/{id}")
    public String deletePizza(@PathVariable Long id) {
        pizzaRepository.deleteById(id);  // Elimina la pizza dal database
        return "redirect:/pizzas";  //
    }
}









