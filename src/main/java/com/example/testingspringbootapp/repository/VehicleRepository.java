package com.example.testingspringbootapp.repository;

import com.example.testingspringbootapp.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
