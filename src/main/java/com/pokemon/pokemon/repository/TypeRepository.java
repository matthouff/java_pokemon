package com.pokemon.pokemon.repository;

import com.pokemon.pokemon.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Integer> {

    Type findByName(String typeName);

}
