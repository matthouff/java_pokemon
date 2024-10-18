package com.pokemon.pokemon.entities;

import jakarta.persistence.*;

//Permet de sauvegarder le Jwt généré
@Entity
@Table(name = "jwt")
public class Jwt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID",columnDefinition="INT")
    private int id;
    private boolean desactive;
    private boolean expire;
    private String value;

    // DETACH : Permet de dire que l'on ne veut pas supprimer l'utilisateur lors de la suppression du token
    // MERGE : interdi la création du token si l'utilisateur n'existe pas.
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    public Jwt() {
    }

    public Jwt(int id, boolean desactive, boolean expire, String value, User user) {
        this.id = id;
        this.desactive = desactive;
        this.expire = expire;
        this.value = value;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDesactive() {
        return desactive;
    }

    public void setDesactive(boolean desactive) {
        this.desactive = desactive;
    }

    public boolean isExpire() {
        return expire;
    }

    public void setExpire(boolean expire) {
        this.expire = expire;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
