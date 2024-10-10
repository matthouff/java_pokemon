package com.pokemon.pokemon.controller;

import com.pokemon.pokemon.entities.Pokemon;

import com.pokemon.pokemon.services.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "pokemon")
@CrossOrigin(origins = "http://localhost:4200")
public class PokemonController {

    PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Pokemon create(@RequestBody Pokemon pokemon){
        System.err.println(pokemon.getName());

        Pokemon pokemonExist = pokemonService.findByName(pokemon.getName().toLowerCase());
        System.out.println(pokemonExist != null);
        if(pokemonExist == null){
            return pokemonService.create(pokemon);
        }
        return null;
    }

    @PutMapping(path = "edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Pokemon update(@RequestBody Pokemon pokemon){
        Pokemon pokemonExist = pokemonService.findPokemonById(pokemon.getId());

        if(pokemonExist != null){
            return pokemonService.update(pokemon);
        }
        return null;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Pokemon> getAll(){
        return pokemonService.getAll();
    }

    // L'id dans le path doit avoir le même nom que celui mis dans le paramètre (ici: pokemonId)
    @GetMapping(path = "{pokemonId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Pokemon getPokemon(@PathVariable Long pokemonId){
        return pokemonService.findPokemonById(pokemonId);
    }

    @GetMapping(path = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Pokemon[] getPokemon(@RequestParam String name){
        return pokemonService.findAllByName(name);
    }

    @DeleteMapping(path = "{pokemonId}")
    public void delete(@PathVariable Long pokemonId){
        pokemonService.delete(pokemonId);
    }
}
