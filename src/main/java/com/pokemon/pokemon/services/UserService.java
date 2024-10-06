package com.pokemon.pokemon.services;

import com.pokemon.pokemon.entities.User;
import com.pokemon.pokemon.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void inscription(User user){
        userRepository.save(user);
    }
}
