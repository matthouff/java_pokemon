package com.pokemon.pokemon.services;

import com.pokemon.pokemon.entities.Pokemon;
import com.pokemon.pokemon.repository.PokemonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonService implements IPokemonService {
    private PokemonRepository pokemonRepository;

    // Appeler les dépendances utilisées
    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public void create(Pokemon pokemon){
        pokemonRepository.save(pokemon);
    }
    public void delete(Long pokemonId){
        pokemonRepository.deleteById(pokemonId);
    }

    public List<Pokemon> getAll(){
        return pokemonRepository.findAll();
    }

    public Pokemon findByName(String name) {
        return pokemonRepository.findByName(name);
    }

    // Comme nous ne sommes pas sur que le Pokémon existe, on le déclare en Optionnal
    public Pokemon findPokemonById(Long pokemonId) {
        Optional<Pokemon> optionnalSelected = pokemonRepository.findById(pokemonId);
        return optionnalSelected.orElse(null);
    }

    @Override
    public Pokemon editPokemon(Pokemon pokemon) {
        return null;
    }




//    private PokemonRepository pokemonRepository;
//
//    @Autowired
//    public PokemonService(PokemonRepository pokemonRepository) {
//        this.pokemonRepository = pokemonRepository;
//    }
//
//    @Override
//    public Pokemon createPokemon(Pokemon pokemon){
//        return pokemonRepository.save(pokemon);
//    }

}
