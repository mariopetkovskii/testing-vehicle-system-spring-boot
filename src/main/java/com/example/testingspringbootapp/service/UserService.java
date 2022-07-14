package com.example.testingspringbootapp.service;

import com.example.testingspringbootapp.model.Role;
import com.example.testingspringbootapp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(String username, String password, String repeatPassword, String name, String surname);

    User addVehicleToFavourites(User user, Long id);


    User removeVehicleFromFavorites(User user, Long id);


}
