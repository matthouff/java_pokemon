package com.pokemon.pokemon.services;

import com.pokemon.pokemon.entities.Type;
import com.pokemon.pokemon.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    private TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public Type create (Type type){
        return typeRepository.save(type);
    }

    public void delete (int typeId){
        typeRepository.deleteById(typeId);
    }

    public List<Type> typeList(){
        return typeRepository.findAll();
    }

    public Type getTypeByName(String typeName){
        System.out.println(typeName);
        return typeRepository.findByName(typeName);
    }

    public Type getTypeById(int id){
        return typeRepository.findById(id).orElse(null);
    }
}
