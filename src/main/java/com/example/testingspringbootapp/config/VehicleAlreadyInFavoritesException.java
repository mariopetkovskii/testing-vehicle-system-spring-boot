package com.example.testingspringbootapp.config;

public class VehicleAlreadyInFavoritesException extends RuntimeException{
    public VehicleAlreadyInFavoritesException() {
        super("Vehicle is already in favorites");
    }
}
