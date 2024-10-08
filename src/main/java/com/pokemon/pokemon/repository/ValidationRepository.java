package com.pokemon.pokemon.repository;

import com.pokemon.pokemon.entities.Validation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ValidationRepository extends CrudRepository<Validation, Long> {
    Optional<Validation> findByCode(String code);

    Validation findByUserId(Long id);
}
