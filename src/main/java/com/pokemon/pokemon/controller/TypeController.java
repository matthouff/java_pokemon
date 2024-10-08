package com.pokemon.pokemon.controller;

import com.pokemon.pokemon.entities.Type;
import com.pokemon.pokemon.services.TypeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "type")
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Type> typeList(){
        return typeService.typeList();
    }

    @GetMapping(path = "{typeId}")
    public Type getTypeById(@PathVariable int typeId){
        return typeService.getTypeById(typeId);
    }

}
