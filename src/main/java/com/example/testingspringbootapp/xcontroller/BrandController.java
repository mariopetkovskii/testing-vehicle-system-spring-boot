package com.example.testingspringbootapp.xcontroller;

import com.example.testingspringbootapp.model.VehicleBrand;
import com.example.testingspringbootapp.service.VehicleBrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/brands")
public class BrandController {
    private final VehicleBrandService vehicleBrandService;

    @GetMapping
    public String getBrands(Model model){
        List<VehicleBrand> vehicleBrandList = this.vehicleBrandService.findAll();
        model.addAttribute("brands", vehicleBrandList);
        model.addAttribute("bodyContent", "brands");
        return "master-template";
    }

    @GetMapping("/add")
    public String getBrandsAddPage(Model model){
        model.addAttribute("bodyContent", "add-brand");
        return "master-template";
    }

    @PostMapping("/add")
    public String addBrand(@RequestParam String name){
        this.vehicleBrandService.add(name);
        return "redirect:/brands";
    }

    @PostMapping("/delete/{id}")
    public String deleteBrand(@PathVariable Long id){
        this.vehicleBrandService.delete(id);
        return "redirect:/brands";
    }
}
