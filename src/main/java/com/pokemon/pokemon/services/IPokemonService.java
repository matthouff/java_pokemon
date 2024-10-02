package com.pokemon.pokemon.services;

import com.pokemon.pokemon.entities.Pokemon;

import java.util.List;
import java.util.Optional;

public interface IPokemonService {

    public void create(Pokemon pokemon);
    public void delete(Long pokemonId);

    public List<Pokemon> getAll();
    public Pokemon findByName(String name);
    public Pokemon findPokemonById(Long pokemonId);

    public Pokemon editPokemon(Pokemon pokemon);
}
