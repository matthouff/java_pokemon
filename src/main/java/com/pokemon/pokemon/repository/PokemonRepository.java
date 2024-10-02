package com.pokemon.pokemon.repository;

import com.pokemon.pokemon.entities.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository // Pas obligatoire puisque qu'il étend déjà de JpaRepository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    // Spring sait que quand il y a "findBy" il doit chercher l'élément qui sera juste après (Name)
    Pokemon findByName(String name);

}
