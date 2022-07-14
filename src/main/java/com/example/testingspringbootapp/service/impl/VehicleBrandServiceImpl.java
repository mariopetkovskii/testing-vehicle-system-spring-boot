package com.example.testingspringbootapp.service.impl;

import com.example.testingspringbootapp.model.VehicleBrand;
import com.example.testingspringbootapp.model.exceptions.InvalidVehicleBrandIdException;
import com.example.testingspringbootapp.repository.VehicleBrandRepository;
import com.example.testingspringbootapp.service.VehicleBrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VehicleBrandServiceImpl implements VehicleBrandService {

    private final VehicleBrandRepository vehicleBrandRepository;

    @Override
    public VehicleBrand findById(Long id) {
        return this.vehicleBrandRepository.findById(id).orElseThrow(InvalidVehicleBrandIdException::new);
    }

    @Override
    public List<VehicleBrand> findAll() {
        return this.vehicleBrandRepository.findAll();
    }

    @Override
    public VehicleBrand add(String name) {
        return this.vehicleBrandRepository.save(new VehicleBrand(name));
    }

    @Override
    public VehicleBrand edit(Long id, String name) {
        VehicleBrand vehicleBrand = this.vehicleBrandRepository.findById(id).orElseThrow(InvalidVehicleBrandIdException::new);
        vehicleBrand.setName(name);
        return this.vehicleBrandRepository.save(vehicleBrand);
    }

    @Override
    public void delete(Long id) {
        this.vehicleBrandRepository.deleteById(id);
    }

    @Override
    public VehicleBrand findByName(String name) {
        return this.vehicleBrandRepository.findByName(name);
    }
}
