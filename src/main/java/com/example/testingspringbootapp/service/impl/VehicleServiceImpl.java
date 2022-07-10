package com.example.testingspringbootapp.service.impl;

import com.example.testingspringbootapp.model.Vehicle;
import com.example.testingspringbootapp.model.VehicleBrand;
import com.example.testingspringbootapp.model.VehicleType;
import com.example.testingspringbootapp.model.exceptions.InvalidVehicleIdException;
import com.example.testingspringbootapp.repository.VehicleRepository;
import com.example.testingspringbootapp.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> findAll() {
        return this.vehicleRepository.findAll();
    }

    @Override
    public Vehicle findById(Long id) {
        return this.vehicleRepository.findById(id).orElseThrow(InvalidVehicleIdException::new);
    }

    @Override
    public Vehicle add(VehicleBrand vehicleBrand, String model, VehicleType type, Double price) {
        return this.vehicleRepository.save(new Vehicle(vehicleBrand, model, type, price));
    }

    @Override
    public Vehicle edit(Long id, VehicleBrand vehicleBrand, String model, VehicleType type, Double price) {
        Vehicle vehicle = this.vehicleRepository.findById(id).orElseThrow(InvalidVehicleIdException::new);
        vehicle.setVehicleBrand(vehicleBrand);
        vehicle.setModel(model);
        vehicle.setType(type);
        vehicle.setPrice(price);
        return this.vehicleRepository.save(vehicle);
    }

    @Override
    public void delete(Long id) {
        this.vehicleRepository.deleteById(id);
    }

    @Override
    public List<Vehicle> listAllByGivenData(Double price, VehicleType type, String model) {
        if(price != null && type != null && model != null && !model.isEmpty()){
            return this.vehicleRepository.findAllByPriceLessThanAndTypeLikeAndModelLike(price, type, model);
        }else if(price != null && type != null){
            return this.vehicleRepository.findAllByPriceLessThanAndTypeLike(price, type);
        }else if(price != null && model != null && !model.isEmpty()){
            return this.vehicleRepository.findAllByPriceLessThanAndModelLike(price, model);
        }else if(type != null && model != null && !model.isEmpty()){
            return this.vehicleRepository.findAllByTypeLikeAndModelLike(type, model);
        }else if(price != null){
            return this.vehicleRepository.findAllByPriceLessThan(price);
        }else if(type != null){
            return this.vehicleRepository.findAllByType(type);
        }else if(model != null){
            return this.vehicleRepository.findAllByModelLike(model);
        }else
        return this.vehicleRepository.findAll();
    }
}
