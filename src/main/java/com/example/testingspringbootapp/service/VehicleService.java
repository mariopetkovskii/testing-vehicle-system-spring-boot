package com.example.testingspringbootapp.service;

import com.example.testingspringbootapp.model.Vehicle;
import com.example.testingspringbootapp.model.VehicleBrand;
import com.example.testingspringbootapp.model.VehicleType;

import java.util.List;

public interface VehicleService {
    List<Vehicle> findAll();

    Vehicle findById(Long id);

    Vehicle add(VehicleBrand vehicleBrand, String model, VehicleType type, Double price);

    Vehicle edit(Long id, VehicleBrand vehicleBrand, String model, VehicleType type, Double price);

    void delete(Long id);
}
