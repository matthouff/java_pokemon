package com.pokemon.pokemon.services;

import com.pokemon.pokemon.entities.Pokemon;
import com.pokemon.pokemon.repository.PokemonRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PokemonService implements IPokemonService {
    private PokemonRepository pokemonRepository;

    // Appeler les dépendances utilisées
    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public Pokemon create(Pokemon pokemon){
        return pokemonRepository.save(pokemon);
    }
    public Pokemon update(Pokemon pokemon){
        // Mettre à jour uniquement updatedAt
        pokemon.setUpdatedAt(new Date()); // Met à jour la date actuelle
        // Vous pouvez également mettre à jour d'autres champs si nécessaire
        return pokemonRepository.save(pokemon); // Sauvegarder les changements
    }
    public void delete(Long pokemonId){
        pokemonRepository.deleteById(pokemonId);
    }

    public List<Pokemon> getAll(){
        return pokemonRepository.findAll().stream().sorted((x,y) -> x.getCreatedAt().compareTo(y.getCreatedAt())).collect(Collectors.toList());
    }

    public Pokemon[] findAllByName(String name) {
        System.out.println(name);
        return pokemonRepository.findByNameContaining(name);
    }

    public Pokemon findByName(String name) {
        return pokemonRepository.findByName(name);
    }

    // Comme nous ne sommes pas sur que le Pokémon existe, on le déclare en Optionnal
    public Pokemon findPokemonById(Long pokemonId) {
        Optional<Pokemon> optionnalSelected = pokemonRepository.findById(pokemonId);
        return optionnalSelected.orElse(null);
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
