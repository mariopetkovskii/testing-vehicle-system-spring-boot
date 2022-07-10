package com.example.testingspringbootapp.repository;

import com.example.testingspringbootapp.model.Vehicle;
import com.example.testingspringbootapp.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findAllByPriceLessThanAndTypeLikeAndModelLike(Double price, VehicleType type, String model);
    List<Vehicle> findAllByPriceLessThanAndTypeLike(Double price, VehicleType type);
    List<Vehicle> findAllByPriceLessThanAndModelLike(Double price, String model);
    List<Vehicle> findAllByTypeLikeAndModelLike(VehicleType type, String model);
    List<Vehicle> findAllByPriceLessThan(Double price);
    List<Vehicle> findAllByType(VehicleType type);
    List<Vehicle> findAllByModelLike(String model);
}
