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

    public Long getId() {
        return id;
    }

    public VehicleBrand getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(VehicleBrand vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
    }
}
