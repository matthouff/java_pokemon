package com.pokemon.pokemon.repository;

import com.pokemon.pokemon.TypeDeRole;
import com.pokemon.pokemon.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByLibelle(TypeDeRole libelle);
}
