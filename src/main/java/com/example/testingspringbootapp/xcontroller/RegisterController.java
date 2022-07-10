package com.example.testingspringbootapp.xcontroller;

import com.example.testingspringbootapp.model.Role;
import com.example.testingspringbootapp.model.exceptions.NotGoodException;
import com.example.testingspringbootapp.model.exceptions.PasswordsDoNotMatchException;
import com.example.testingspringbootapp.service.AuthService;
import com.example.testingspringbootapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final AuthService authService;
    private final UserService userService;

    public RegisterController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent","register");
        return "master-template";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam(required = false) Role role) {
        try{
            Role role1= Role.ROLE_USER;
            this.userService.register(username, password, repeatedPassword, name, surname,role1);
            return "redirect:/login";
        } catch (NotGoodException | PasswordsDoNotMatchException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }
}
