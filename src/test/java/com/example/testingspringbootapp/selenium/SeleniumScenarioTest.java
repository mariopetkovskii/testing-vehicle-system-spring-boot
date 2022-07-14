package com.example.testingspringbootapp.selenium;

import com.example.testingspringbootapp.model.*;
import com.example.testingspringbootapp.repository.UserRepository;
import com.example.testingspringbootapp.service.UserService;
import com.example.testingspringbootapp.service.VehicleBrandService;
import com.example.testingspringbootapp.service.VehicleService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    UserService userService;

    @Autowired
    VehicleBrandService vehicleBrandService;

    @Autowired
    UserDetailsService userDetailsService;

    private HtmlUnitDriver driver;

    private static VehicleBrand vehicleBrand1;
    private static VehicleBrand vehicleBrand2;

    private static String user = "user";
    private static String admin = "admin";

    private static boolean dataInitialized = false;

    @BeforeEach
    private void setup(){
        this.driver=new HtmlUnitDriver(true);
        initData();
    }

    @AfterEach
    public void destroy(){
        if(this.driver != null){
            this.driver.close();
        }
    }

    private void initData(){
        if(!dataInitialized){
            vehicleBrand1=vehicleBrandService.add("Volkswagen");
            vehicleBrand2=vehicleBrandService.add("Toyota");


            /*regularUser=userService.register(user,user,user,user,user);
            adminUser=userService.register(admin,admin,admin,admin,admin);
            adminUser.setRole(Role.ROLE_ADMINISTRATOR);*/

            dataInitialized=true;
        }
    }

    @Test
    public void testScenario() throws Exception{

        VehiclesPage vehiclesPage=VehiclesPage.to(this.driver);
        vehiclesPage.assertElements(4,0,0,0,0);
        LoginPage loginPage=LoginPage.openLogin(this.driver);

        vehiclesPage=LoginPage.doLogin(this.driver,loginPage,admin,admin);
        vehiclesPage.assertElements(4,4,4,4,1);

        vehiclesPage=AddOrEditVehicle.addVehicle(this.driver,vehicleBrand2.getName(),"corolla", String.valueOf(VehicleType.CAR),"8000.0");
        vehiclesPage.assertElements(5,5,5,5,1);

        vehiclesPage=AddOrEditVehicle.addVehicle(this.driver,vehicleBrand1.getName(),"golf", String.valueOf(VehicleType.CAR),"10000.0");
        vehiclesPage.assertElements(6,6,6,6,1);

        vehiclesPage.getDeleteButtons().get(1).click();
        vehiclesPage.assertElements(5,5,5,5,1);


        vehiclesPage=AddOrEditVehicle.editVehicle(this.driver,vehiclesPage.getEditButtons().get(0),"Audi","A3", String.valueOf(VehicleType.CAR),"32550.0");
        vehiclesPage=VehiclesPage.to(this.driver);
        vehiclesPage.assertElements(5,5,5,5,1);

        loginPage=LoginPage.logout(this.driver);
        loginPage=LoginPage.openLogin(this.driver);
        vehiclesPage=LoginPage.doLogin(this.driver,loginPage,user,user);
        vehiclesPage.assertElements(5,0,0,5,0);

        vehiclesPage.getFavoriteButtons().get(0).click();
        Assert.assertEquals("url do not match","http://localhost:9999/favorite",this.driver.getCurrentUrl());

        FavoriteVehiclesPage favoriteVehiclesPage=FavoriteVehiclesPage.init(this.driver);
        favoriteVehiclesPage.assertElements(1,1);

        favoriteVehiclesPage.getRemoveButtons().get(0).click();
        favoriteVehiclesPage.assertElements(0,0);
    }


}