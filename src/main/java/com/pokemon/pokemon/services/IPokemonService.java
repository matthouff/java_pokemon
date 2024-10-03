package com.pokemon.pokemon.services;

import com.pokemon.pokemon.entities.Pokemon;

import java.util.List;
import java.util.Optional;

public interface IPokemonService {

    public Pokemon create(Pokemon pokemon);
    public void delete(Long pokemonId);

    public List<Pokemon> getAll();
    public Pokemon findByName(String name);
    public Pokemon findPokemonById(Long pokemonId);
}
