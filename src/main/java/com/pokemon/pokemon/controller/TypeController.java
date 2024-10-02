package com.pokemon.pokemon.controller;

import com.pokemon.pokemon.entities.Type;
import com.pokemon.pokemon.services.TypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "type")
@CrossOrigin(origins = "http://localhost:4200") // Pour autoriser Angular à appeler cette API
public class TypeController {
    TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @PostMapping()
    public Type createType(@RequestBody Type type){
        Type typeExist = typeService.getTypeByName(type.getName());
        if(typeExist == null){
            return typeService.create(type);
        }
        return null;
    }

    @DeleteMapping(path = "{typeId}")
    public void deleteType(@PathVariable int typeId){
        typeService.delete(typeId);
    }

    @GetMapping()
    public List<Type> typeList(){
        return typeService.typeList();
    }

    @GetMapping(path = "{typeId}")
    public Type getTypeById(@PathVariable int typeId){
        return typeService.getTypeById(typeId);
    }

}