package com.example.testingspringbootapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class VehicleBrand {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public VehicleBrand(String name) {
        this.name = name;
    }
}
