package com.example.testingspringbootapp.config;

import com.example.testingspringbootapp.model.Vehicle;
import com.example.testingspringbootapp.model.VehicleBrand;
import com.example.testingspringbootapp.model.VehicleType;
import com.example.testingspringbootapp.repository.VehicleBrandRepository;
import com.example.testingspringbootapp.repository.VehicleRepository;
import com.example.testingspringbootapp.service.VehicleBrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final VehicleBrandService vehicleBrandService;
    private final VehicleBrandRepository vehicleBrandRepository;
    private final VehicleRepository vehicleRepository;

    @PostConstruct
    public void initData(){
        VehicleBrand vehicleBrand1 = new VehicleBrand("BMW");
        VehicleBrand vehicleBrand2 = new VehicleBrand("Audi");
        VehicleBrand vehicleBrand3 = new VehicleBrand("Peugeot");
        VehicleBrand vehicleBrand4 = new VehicleBrand("Mercedes-Benz");

        this.vehicleBrandRepository.save(vehicleBrand1);
        this.vehicleBrandRepository.save(vehicleBrand2);
        this.vehicleBrandRepository.save(vehicleBrand3);
        this.vehicleBrandRepository.save(vehicleBrand4);

        Vehicle vehicle1 = new Vehicle(vehicleBrand1, "5", VehicleType.CAR, 30000.0);
        Vehicle vehicle2 = new Vehicle(vehicleBrand2, "A3", VehicleType.CAR, 32550.0);
        Vehicle vehicle3 = new Vehicle(vehicleBrand3, "407", VehicleType.CAR, 31222.0);
        Vehicle vehicle4 = new Vehicle(vehicleBrand4, "A180", VehicleType.CAR, 19000.0);

        this.vehicleRepository.save(vehicle1);
        this.vehicleRepository.save(vehicle2);
        this.vehicleRepository.save(vehicle3);
        this.vehicleRepository.save(vehicle4);


    }
}
