package com.pokemon.pokemon.repository;

import com.pokemon.pokemon.entities.Jwt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface JwtRepository extends CrudRepository<Jwt, Integer> {
    Optional<Jwt> findByValueAndDesactiveAndExpire(String email, boolean expire, boolean desactive);
    
    @Query("SELECT j FROM Jwt j WHERE j.user.email = :email AND j.expire = false AND j.desactive = false")
    Optional<Jwt> findUserValidToken(@Param("email") String email);

    @Query("SELECT j FROM Jwt j WHERE j.user.email = :email")
    Stream<Jwt> findByUserEmail(@Param("email") String email);

}
