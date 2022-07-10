package com.example.testingspringbootapp.xcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/access_denied")
public class AccessDeniedController {

    @GetMapping
    public String getAccessDeniedPage(Model model){
        model.addAttribute("bodyContent", "access_denied");
        return "master-template";
    }
}
