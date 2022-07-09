package com.example.testingspringbootapp.repository;

import com.example.testingspringbootapp.model.VehicleBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleBrandRepository extends JpaRepository<VehicleBrand, Long> {
}
