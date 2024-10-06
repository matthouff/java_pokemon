package com.pokemon.pokemon.services;

import com.pokemon.pokemon.TypeDeRole;
import com.pokemon.pokemon.entities.Role;
import com.pokemon.pokemon.entities.User;
import com.pokemon.pokemon.entities.Validation;
import com.pokemon.pokemon.repository.RoleRepository;
import com.pokemon.pokemon.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ValidationService validationService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ValidationService validationService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.validationService = validationService;
    }

    public void inscription(User user){
        if(!user.getEmail().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")){
            throw new RuntimeException("Votre mail est invalide");
        }

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        if(userOptional.isPresent()){
            throw new RuntimeException("Vous avez déjà un compte avec cet email");
        }

        String passwordCrypte = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(passwordCrypte);


        // Si un Role n'existe pas, il le créer en même temps, sinon il attribut celui existant
        Optional<Role> existingRole = roleRepository.findByLibelle(TypeDeRole.USER);
        if (existingRole.isPresent()) {
            System.out.println("Coucou");
            // Utiliser le rôle existant
            user.setRole(existingRole.get());
        } else {
            System.out.println("Coucou2");
            // Insérer le nouveau rôle
            Role newRole = new Role();
            newRole.setLibelle(TypeDeRole.USER);
            roleRepository.save(newRole);
            user.setRole(newRole);
        }

        user = userRepository.save(user);
        validationService.enregistrer(user);
    }

    public void activation(Map<String, String> activation) {
        Validation validation = validationService.lireEnFonctionDuCode(activation.get("code"));

        if (Instant.now().isAfter(validation.getExpiration())){
            throw new RuntimeException("Votre code à expiré");
        }

        User user = userRepository.findById(validation.getUser().getId()).orElseThrow(() -> new RuntimeException("Utilisateur inconnu"));

        user.setActive(true);
        userRepository.save(user);
    }
}