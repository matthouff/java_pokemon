package com.pokemon.pokemon.repository;

import com.pokemon.pokemon.entities.Jwt;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JwtRepository extends CrudRepository<Jwt, Integer> {
    Optional<Jwt> findByValue(String value);
}
