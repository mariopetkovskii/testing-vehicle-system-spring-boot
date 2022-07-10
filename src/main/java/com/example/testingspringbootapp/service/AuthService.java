package com.example.testingspringbootapp.service;


import com.example.testingspringbootapp.model.User;

public interface AuthService {
    User login(String username, String password);
}