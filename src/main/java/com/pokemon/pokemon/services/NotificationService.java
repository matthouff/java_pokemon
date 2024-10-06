package com.pokemon.pokemon.services;

import com.pokemon.pokemon.entities.Validation;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    // Va permettre d'envoyer un mail de confirmation de création de compte

    JavaMailSender javaMailSender;

    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void envoyer(Validation validation){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@pokemonapp.com");
        mailMessage.setTo(validation.getUser().getEmail());
        mailMessage.setSubject("Votre code d'activation");

        String text = String.format(
                "Bonjours %s, <br/><br/> Voici votre code d'activation: %s <br/><br/> Bienvenu dans le pokédex.",
                    validation.getUser().getName(),
                    validation.getCode()
                );
        mailMessage.setText(text);

        javaMailSender.send(mailMessage);
    }
}
