package com.pokemon.pokemon.controller;

import com.pokemon.pokemon.dto.AuthentificationDto;
import com.pokemon.pokemon.entities.User;
import com.pokemon.pokemon.repository.UserRepository;
import com.pokemon.pokemon.security.JwtService;
import com.pokemon.pokemon.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "user")
public class UserController {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private UserRepository userRepository;
    private JwtService jwtService;

    public UserController(AuthenticationManager authenticationManager, UserService userService, UserRepository userRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping(path = "inscription")
    public ResponseEntity<String> inscription(@RequestBody User user){
        try{
            userService.inscription(user);
            // Créer une réponse personnalisée
            return ResponseEntity.ok()
                    .header("X-Custom-Header", "valeur personnalisée")
                    .body("Inscription réussie ! Veuillez activer votre compte.");
        }catch (Exception e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }

    }

    @PostMapping(path = "activation")
    public ResponseEntity<String> activation(@RequestBody Map<String, String> activation){
        try {
            userService.activation(activation);
            return ResponseEntity.ok()
                    .header("X-Custom-Header", "valeur personnalisée")
                    .body("Activation réussie !");
        }catch (Exception e){
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @PostMapping(path = "deconnexion")
    public void deconnexion(){
        System.out.println("hey");
        jwtService.deconnexion();
    }

    // Map<String, String> c'est le token et la valeur du token
    @PostMapping(path = "connexion")
    public Map<String, String> connexion(@RequestBody AuthentificationDto authentificationDto){
        System.out.println(authentificationDto);
        Optional<User> user = userRepository.findByEmail(authentificationDto.email());

        final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authentificationDto.email(), authentificationDto.password())
        );

        if (!user.get().isActive()){
            throw new RuntimeException("Votre compte n'a pas été activé");
        }

        if(authentication.isAuthenticated()){
            return jwtService.generate(authentificationDto.email());
        }

        return null;
    }

}
