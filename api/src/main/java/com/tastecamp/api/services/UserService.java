package com.tastecamp.api.services;

import org.springframework.stereotype.Service;

import com.tastecamp.api.dtos.UserDTO;
import com.tastecamp.api.models.UserModel;
import com.tastecamp.api.repositories.UserRepository;

@Service
public class UserService {

    final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public UserModel createUser(UserDTO userDTO) {
        UserModel user = new UserModel(userDTO);
        return userRepository.save(user);
    }
}
