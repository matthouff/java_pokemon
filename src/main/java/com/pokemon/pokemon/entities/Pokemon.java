package com.pokemon.pokemon.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pokemon")
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID",columnDefinition="BIGINT")
    private Long id;

    @Column(unique = true, name="NAME")
    private String name;

    @Column(name="HP")
    private int hp;

    @Column(name="CP")
    private int cp;

    @Column(name="PICTURE")
    private String picture;

    @Column(name="CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)  // Enregistre la date et l'heure
    private Date createdAt;

    @Column(name="UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)  // Enregistre la date et l'heure
    private Date updatedAt;

    // MERGE permet de se lier aux autres données, si les types n'existaient pas il faudrait mettre PERSIST
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(
            name = "pokemon_type",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private List<Type> types;

    public Pokemon() {
    }

    public Pokemon(Long id, String name, int hp, int cp, String picture, List<Type> types) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.cp = cp;
        this.picture = picture;
        this.types = types;
    }


    // Utilisation des annotations pour gérer automatiquement les dates
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }



    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
