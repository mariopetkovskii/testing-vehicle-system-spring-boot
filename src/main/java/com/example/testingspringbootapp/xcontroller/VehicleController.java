package com.example.testingspringbootapp.xcontroller;

import com.example.testingspringbootapp.model.Vehicle;
import com.example.testingspringbootapp.model.VehicleBrand;
import com.example.testingspringbootapp.model.VehicleType;
import com.example.testingspringbootapp.service.VehicleBrandService;
import com.example.testingspringbootapp.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(value = {"/","/vehicles"})
public class VehicleController {

    private final VehicleBrandService vehicleBrandService;
    private final VehicleService vehicleService;

    @GetMapping
    public String vehiclesPage(@RequestParam(required = false) Double price, @RequestParam(required = false) VehicleType type, Model model){
        List<Vehicle> vehicleList = this.vehicleService.findAll();
        model.addAttribute("vehicles", vehicleList);
        model.addAttribute("bodyContent", "veh-page");
        return "master-template";
    }

    @GetMapping("/add")
    public String addVehiclePage(Model model){
        List<VehicleBrand> vehicleBrands = this.vehicleBrandService.findAll();
        model.addAttribute("brands", vehicleBrands);
        model.addAttribute("bodyContent", "add-form");
        return "master-template";
    }

    @GetMapping("/edit-form/{id}")
    public String editVehiclePage(@PathVariable Long id, Model model){
        List<VehicleBrand> vehicleBrands = this.vehicleBrandService.findAll();
        model.addAttribute("brands", vehicleBrands);
        Vehicle vehicle = this.vehicleService.findById(id);
        model.addAttribute("vehicles", vehicle);
        return "add-form";
    }

    @PostMapping("/add")
    public String addVehicle(@RequestParam(required = false)Long id,
                            @RequestParam VehicleBrand brand,
                             @RequestParam String model,
                             @RequestParam VehicleType type,
                             @RequestParam Double price){
        if (id != null) {
            this.vehicleService.edit(id, brand, model, type, price);
        } else {
            this.vehicleService.add(brand, model, type, price);
        }
        //this.vehicleService.add(brand, model, type, price);
        return "redirect:/vehicles";
    }

    @PostMapping("/delete/{id}")
    public String deleteVeh(@PathVariable Long id){
        this.vehicleService.delete(id);
        return "redirect:/vehicles";
    }
}
