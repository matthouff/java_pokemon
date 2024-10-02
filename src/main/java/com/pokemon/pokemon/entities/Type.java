package com.pokemon.pokemon.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID",columnDefinition="INT")
    private int id;
    @Column(name = "NAME", length = 20)
    private String name;
    @Column(name = "COLOR", length = 7)
    private String color;

    // Si un pokémon avait plusieurs types mais un type n'a qu'un pokémon, on aurait mis:
//    @ManyToOne
//    @JoinColumn(name = "pokemon_id") // Nom de la colonne de la table type dans la bdd si elle existait
    @ManyToMany(mappedBy = "types")
    private List<Pokemon> pokemon;


    public Type() {
    }

    public Type(int id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
