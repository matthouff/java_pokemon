package com.pokemon.pokemon.controller;

import com.pokemon.pokemon.entities.Pokemon;

import com.pokemon.pokemon.services.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "pokemon")
@CrossOrigin(origins = "http://localhost:4200") // Pour autoriser Angular à appeler cette API
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

    @DeleteMapping(path = "{pokemonId}")
    public void delete(@PathVariable Long pokemonId){
        pokemonService.delete(pokemonId);
    }

//    private final Pokedex pokedex;
//    IPokemonService pokemonService;
//    private static long nextId = 1;
//
//    @Autowired
//    public PokemonController(Pokedex pokedex, IPokemonService pokemonService) {
//        this.pokedex = pokedex;
//        this.pokemonService = pokemonService;
//    }
//
//    @GetMapping(path = "string")
//   public String getString(){
//        System.out.println("coucou");
//        return "Chaine de caractère trouvé hehe";
//    }
//
//
//    @GetMapping
//    public List<Pokemon> getAllPokemons() {
//        System.out.println("coucou");
//        return pokedex.getPokemons();
//    }
//
//    @PostMapping
//    public Pokemon createPokemon(@RequestBody Pokemon pokemon) {
//        if (pokemon.getId() == null) {
//            pokemon.setId(nextId++);
//        }
//
//        System.out.println("////Controller////");
//        pokemonService.createPokemon(pokemon);
//        System.out.println("////////");
//        return pokemon;
//    }

    // ... autres méthodes pour mettre à jour, supprimer, etc.
}
