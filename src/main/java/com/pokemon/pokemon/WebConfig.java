package com.pokemon.pokemon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig{

    @Bean
    public WebMvcConfigurer corsConfig(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods(
                                "GET", "POST", "PUT", "DELETE"
                        )
                        .allowedHeaders(
                                HttpHeaders.CONTENT_TYPE,
                                HttpHeaders.AUTHORIZATION,
                                HttpHeaders.ACCEPT
                        )
                        .exposedHeaders("Authorization") // Expose les en-têtes spécifiques
                        .allowCredentials(true); // Autorise l'envoi de cookies ou d'en-têtes avec des informations d'authentification

            }
        };
    }
}