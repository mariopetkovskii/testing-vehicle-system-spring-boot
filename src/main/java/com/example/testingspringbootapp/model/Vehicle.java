package com.example.testingspringbootapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Vehicle {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private VehicleBrand vehicleBrand;

    private String model;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    private Double price;

    @ElementCollection
    private List<Integer> ratings;

    public Vehicle(VehicleBrand vehicleBrand, String model, VehicleType type, Double price) {
        this.vehicleBrand = vehicleBrand;
        this.model = model;
        this.type = type;
        this.price = price;
        this.ratings = new ArrayList<>();
    }
}
