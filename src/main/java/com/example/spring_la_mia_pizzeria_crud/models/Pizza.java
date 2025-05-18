package com.example.spring_la_mia_pizzeria_crud.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pizze")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Il nome della pizza è obbligatorio.")
    @Size(min = 3, max = 50, message = "Il nome della pizza deve essere tra 3 e 50 caratteri.")
    private String nome;

     @NotEmpty(message = "La descrizione è obbligatoria.")
    @Size(min = 10, message = "La descrizione deve essere di almeno 10 caratteri.")
    private String descrizione;

    @NotEmpty(message = "L' URL dell'immagine è obbligatorio.")
    private String fotoUrl;
    @NotNull(message = "Il prezzo non può essere nullo.")
    private double prezzo;
    @NotEmpty(message = "La categoria è obbligatoria.")
    private String categoria;

      @ManyToMany
    @JoinTable(
        name = "pizza_ingredienti",  // Nome della tabella di associazione
        joinColumns = @JoinColumn(name = "pizza_id"),
        inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )

    @NotEmpty(message = "Devi selezionare almeno un ingrediente")
    private List<Ingrediente> ingredienti;

    @Column(nullable = true)
    private String note;  // Nuovo campo per le note

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(List<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }

}