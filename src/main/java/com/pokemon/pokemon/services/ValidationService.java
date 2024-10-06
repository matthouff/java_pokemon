package com.pokemon.pokemon.services;

import com.pokemon.pokemon.entities.User;
import com.pokemon.pokemon.entities.Validation;
import com.pokemon.pokemon.repository.ValidationRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
public class ValidationService {

    private ValidationRepository validationRepository;
    private NotificationService notificationService;

    public ValidationService(ValidationRepository validationRepository, NotificationService notificationService) {
        this.validationRepository = validationRepository;
        this.notificationService = notificationService;
    }

    public void enregistrer(User user){
        Validation validation = new Validation();
        validation.setUser(user);
        Instant creadedAt = Instant.now();
        validation.setCreatedAt(creadedAt);
        Instant expiration = creadedAt.plus(10, ChronoUnit.MINUTES);
        validation.setExpiration(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        validation.setCode(code);

        validationRepository.save(validation);
        notificationService.envoyer(validation);
    }

    public Validation lireEnFonctionDuCode(String code){
        return validationRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Votre code est invalide"));
    }


}
