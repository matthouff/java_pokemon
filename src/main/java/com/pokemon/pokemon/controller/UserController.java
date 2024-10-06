package com.pokemon.pokemon.controller;

import com.pokemon.pokemon.entities.User;
import com.pokemon.pokemon.repository.UserRepository;
import com.pokemon.pokemon.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(path = "inscription")
    public void inscription(@RequestBody User user){
        userService.inscription(user);
    }

}
