package com.example.testingspringbootapp.service;

import com.example.testingspringbootapp.model.VehicleBrand;

import java.util.List;

public interface VehicleBrandService {
    VehicleBrand findById(Long id);

    List<VehicleBrand> findAll();

    VehicleBrand add(String name);

    VehicleBrand edit(Long id, String name);

    void delete(Long id);

    VehicleBrand findByName(String name);
}
