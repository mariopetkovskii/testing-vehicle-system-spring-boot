package com.example.testingspringbootapp.xcontroller;

import com.example.testingspringbootapp.config.VehicleAlreadyInFavoritesException;
import com.example.testingspringbootapp.model.User;
import com.example.testingspringbootapp.model.Vehicle;
import com.example.testingspringbootapp.model.VehicleBrand;
import com.example.testingspringbootapp.model.VehicleType;
import com.example.testingspringbootapp.repository.UserRepository;
import com.example.testingspringbootapp.service.UserService;
import com.example.testingspringbootapp.service.VehicleBrandService;
import com.example.testingspringbootapp.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    private final UserService userService;

    @GetMapping
    public String vehiclesPage(@RequestParam(required = false) String error,
                               @RequestParam(required = false) Double price,
                               @RequestParam(required = false) VehicleType type,
                               @RequestParam(required = false) String containsModel, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Vehicle> vehicleList;
        if(price == null && type == null && containsModel == null){
            vehicleList = this.vehicleService.findAll();
        }
        else{
            vehicleList = this.vehicleService.listAllByGivenData(price, type, containsModel);
        }
        model.addAttribute("vehicles", vehicleList);
        model.addAttribute("bodyContent", "veh-page");
        return "master-template";
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @GetMapping("/add")
    public String addVehiclePage(Model model){
        List<VehicleBrand> vehicleBrands = this.vehicleBrandService.findAll();
        model.addAttribute("brands", vehicleBrands);
        model.addAttribute("bodyContent", "add-form");
        return "master-template";
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @GetMapping("/edit-form/{id}")
    public String editVehiclePage(@PathVariable Long id, Model model){
        List<VehicleBrand> vehicleBrands = this.vehicleBrandService.findAll();
        model.addAttribute("brands", vehicleBrands);
        Vehicle vehicle = this.vehicleService.findById(id);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("bodyContent", "add-form");
        return "master-template";
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
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

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping("/delete/{id}")
    public String deleteVeh(@PathVariable Long id){
        this.vehicleService.delete(id);
        return "redirect:/vehicles";
    }

    @PostMapping("/favorite/add/{id}")
    public String addFavVeh(@PathVariable Long id,
                            Authentication auth){
        User user = (User) auth.getPrincipal();
        try{
            this.userService.addVehicleToFavourites(user, id);
        }catch (VehicleAlreadyInFavoritesException exception)
        {
            return "redirect:/vehicles?error=" + exception.getMessage();
        }
        return "redirect:/favorite";
    }

    @PostMapping("/favorite/remove/{id}")
    public String removeFavVeh(@PathVariable Long id,
                               Authentication auth){
        User user = (User) auth.getPrincipal();
        this.userService.removeVehicleFromFavorites(user, id);
        return "redirect:/favorite";
    }

    @GetMapping("/favorite")
    public String getFavVeh(Authentication auth, Model model){
        User user = (User) auth.getPrincipal();
        List<Vehicle> favVeh = user.getFavouriteVehicles();
        model.addAttribute("vehicles", favVeh);
        model.addAttribute("bodyContent", "favVeh");
        return "master-template";
    }
}
