package com.example.testingspringbootapp.service.impl;


import com.example.testingspringbootapp.model.User;
import com.example.testingspringbootapp.model.exceptions.PasswordsDoNotMatchException;
import com.example.testingspringbootapp.repository.UserRepository;
import com.example.testingspringbootapp.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new PasswordsDoNotMatchException();
        }
        return userRepository.findByUsernameAndPassword(username,
                password).orElseThrow(PasswordsDoNotMatchException::new);
    }

}
