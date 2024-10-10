package com.pokemon.pokemon.security;

import com.pokemon.pokemon.CorsFilter;
import com.pokemon.pokemon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtFilter jwtFilter;

    public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, JwtFilter jwtFilter) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtFilter = jwtFilter;
    }


    ////////////////////////////////// CRÉATION ET VALIDATION DE COMPTE ///////////////////////////////////////

    /*
    * La ligne requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() permet simplement de laisser passer les requêtes
    * préalables CORS, mais les requêtes réelles (comme GET, POST, PUT, DELETE) pour les routes protégées nécessiteront
    * toujours une authentification.
    * En gros, les requêtes OPTIONS sont des "demandes d'autorisation" envoyées par le navigateur avant les vraies
    * requêtes pour vérifier si elles sont autorisées. Cela ne donne pas accès aux ressources protégées, juste
    * permet de vérifier les permissions.
    * */

    // Permet de sécuriser l'application afin de bloquer toutes les routes sauf la route "/inscription"
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                // Donne l'autorisation aux routes défini ci-dessous
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/inscription").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/activation").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/connexion").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                // Donne l'autorisation aux routes défini ci-dessous
                .sessionManagement(
                        httpSecuritySessionManagementConfigurer ->
                            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(new CorsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    ////////////////////////////////// AUTHENTIFICATION ///////////////////////////////////////

    // Vérifie si les informations d'authentification sont correctes
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Permet de se connecter à la base de donnée
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return daoAuthenticationProvider;
    }
}
