package com.example.testingspringbootapp.service.impl;

import com.example.testingspringbootapp.config.VehicleAlreadyInFavoritesException;
import com.example.testingspringbootapp.model.Role;
import com.example.testingspringbootapp.model.User;
import com.example.testingspringbootapp.model.Vehicle;
import com.example.testingspringbootapp.model.exceptions.InvalidVehicleIdException;
import com.example.testingspringbootapp.model.exceptions.NotGoodException;
import com.example.testingspringbootapp.model.exceptions.PasswordsDoNotMatchException;
import com.example.testingspringbootapp.repository.UserRepository;
import com.example.testingspringbootapp.repository.VehicleRepository;
import com.example.testingspringbootapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VehicleRepository vehicleRepository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(()->new UsernameNotFoundException(s));
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname) {
        if (username==null || username.isEmpty()  || password==null || password.isEmpty())
            throw new PasswordsDoNotMatchException();
        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if(this.userRepository.findByUsername(username).isPresent())
            throw new NotGoodException(username);
        User user = new User(username, passwordEncoder.encode(password), name, surname);
        return userRepository.save(user);
    }

    @Override
    public User addVehicleToFavourites(User user, Long id) {
        Vehicle vehicle = this.vehicleRepository.findById(id).orElseThrow(InvalidVehicleIdException::new);
        if(user.getFavouriteVehicles().contains(vehicle))
            throw new VehicleAlreadyInFavoritesException();
        user.getFavouriteVehicles().add(vehicle);
        return this.userRepository.save(user);
    }

    @Override
    public User removeVehicleFromFavorites(User user, Long id) {
        Vehicle vehicle = this.vehicleRepository.findById(id).orElseThrow(InvalidVehicleIdException::new);
        user.getFavouriteVehicles().remove(vehicle);
        return this.userRepository.save(user);
    }
}
