package com.pokemon.pokemon.controller;

import com.pokemon.pokemon.dto.AuthentificationDto;
import com.pokemon.pokemon.entities.User;
import com.pokemon.pokemon.repository.UserRepository;
import com.pokemon.pokemon.security.JwtService;
import com.pokemon.pokemon.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtService jwtService;

    public UserController(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }


    @PostMapping(path = "inscription")
    public void inscription(@RequestBody User user){
        userService.inscription(user);
    }

    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String, String> activation){
        userService.activation(activation);
    }

    // Map<String, String> c'est le token et la valeur du token
    @PostMapping(path = "connexion")
    public Map<String, String> connexion(@RequestBody AuthentificationDto authentificationDto){
        final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authentificationDto.email(), authentificationDto.password())
        );

        if(authentication.isAuthenticated()){
            return jwtService.generate(authentificationDto.email());
        }

        return null;
    }

}
