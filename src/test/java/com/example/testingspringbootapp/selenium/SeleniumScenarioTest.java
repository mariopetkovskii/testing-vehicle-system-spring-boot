package com.example.testingspringbootapp.selenium;

import com.example.testingspringbootapp.model.*;
import com.example.testingspringbootapp.repository.UserRepository;
import com.example.testingspringbootapp.service.UserService;
import com.example.testingspringbootapp.service.VehicleBrandService;
import com.example.testingspringbootapp.service.VehicleService;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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


            /*regularUser=userService.register(user,user,user,user,user);
            adminUser=userService.register(admin,admin,admin,admin,admin);
            adminUser.setRole(Role.ROLE_ADMINISTRATOR);*/

            dataInitialized=true;
        }
    }

    @Order(5)
    @Test
    public void testAddBrandWithAdminAuth() throws Exception{
        VehiclesPage vehiclesPage=VehiclesPage.to(this.driver);
        LoginPage loginPage=LoginPage.openLogin(this.driver);
        vehiclesPage=LoginPage.doLogin(this.driver,loginPage,admin,admin);
        BrandsPage brandsPage=BrandsPage.to(this.driver);
        brandsPage=AddBrand.addBrand(this.driver,"Ford");
        brandsPage.assertElements(6,1);
    }

    @Order(6)
    @Test
    public void testAddVehicleWithAdminAuth() throws Exception{
        VehiclesPage vehiclesPage=VehiclesPage.to(this.driver);
        LoginPage loginPage=LoginPage.openLogin(this.driver);
        vehiclesPage=LoginPage.doLogin(this.driver,loginPage,admin,admin);
        BrandsPage brandsPage=BrandsPage.to(this.driver);
        vehiclesPage=AddOrEditVehicle.addVehicle(this.driver,"Ford","fiesta", String.valueOf(VehicleType.CAR),"4000.0");
        vehiclesPage.assertElements(5,5,5,5,1);
        vehiclesPage=AddOrEditVehicle.addVehicle(this.driver,vehicleBrand1.getName(),"golf", String.valueOf(VehicleType.CAR),"10000.0");
        vehiclesPage.assertElements(6,6,6,6,1);
    }

    @Order(1)
    @Test
    public void testVehiclePageWithoutAuth() throws Exception{
        VehiclesPage vehiclesPage=VehiclesPage.to(this.driver);
        vehiclesPage.assertElements(4,0,0,0,0);
    }

    @Order(2)
    @Test
    public void testFilter() throws Exception{
        VehiclesPage vehiclesPage=VehiclesPage.to(this.driver);

        vehiclesPage=VehiclesPage.to(this.driver);
        vehiclesPage=vehiclesPage.filter(this.driver,"","","");
        vehiclesPage.assertFilter(4);

        vehiclesPage=vehiclesPage.filter(this.driver,"31000.0","","");
        vehiclesPage.assertFilter(2);

        vehiclesPage=vehiclesPage.filter(this.driver,"","CAR","");
        vehiclesPage.assertFilter(3);

        vehiclesPage=vehiclesPage.filter(this.driver,"","","A3");
        vehiclesPage.assertFilter(1);

        vehiclesPage=vehiclesPage.filter(this.driver,"32000.0","CAR","");
        vehiclesPage.assertFilter(2);

        vehiclesPage=vehiclesPage.filter(this.driver,"35000.0","","407");
        vehiclesPage.assertFilter(1);

        vehiclesPage=vehiclesPage.filter(this.driver,"","CAR","5");
        vehiclesPage.assertFilter(1);

        vehiclesPage=vehiclesPage.filter(this.driver,"35000.0","CAR","A3");
        vehiclesPage.assertFilter(1);
    }

    @Order(3)
    @Test
    public void testVehiclePageWithAdminAuth() throws Exception{
        VehiclesPage vehiclesPage=VehiclesPage.to(this.driver);
        LoginPage loginPage=LoginPage.openLogin(this.driver);
        vehiclesPage=LoginPage.doLogin(this.driver,loginPage,admin,admin);
        vehiclesPage.assertElements(4,4,4,4,1);
    }

    @Order(11)
    @Test
    public void testAddFavoriteVehicleToUser() throws Exception{
        VehiclesPage vehiclesPage=VehiclesPage.to(this.driver);
        LoginPage loginPage=LoginPage.openLogin(this.driver);
        vehiclesPage=LoginPage.doLogin(this.driver,loginPage,user,user);
        vehiclesPage=VehiclesPage.to(this.driver);
        vehiclesPage.getFavoriteButtons().get(1).click();
        Assert.assertEquals("url do not match","http://localhost:9999/favorite",this.driver.getCurrentUrl());
        FavoriteVehiclesPage favoriteVehiclesPage=FavoriteVehiclesPage.init(this.driver);
        favoriteVehiclesPage.assertElements(1,1);
    }

    @Order(4)
    @Test
    public void testBrandPageWithAdminAuth() throws Exception{
        VehiclesPage vehiclesPage=VehiclesPage.to(this.driver);
        LoginPage loginPage=LoginPage.openLogin(this.driver);
        vehiclesPage=LoginPage.doLogin(this.driver,loginPage,admin,admin);
        BrandsPage brandsPage=BrandsPage.to(this.driver);
        brandsPage.assertElements(5,1);
    }

    @Order(7)
    @Test
    public void testDeleteVehicleWithAdminAuth() throws Exception{
        VehiclesPage vehiclesPage=VehiclesPage.to(this.driver);
        LoginPage loginPage=LoginPage.openLogin(this.driver);
        vehiclesPage=LoginPage.doLogin(this.driver,loginPage,admin,admin);
        vehiclesPage.getDeleteButtons().get(1).click();
        vehiclesPage.assertElements(5,5,5,5,1);
    }

    @Order(8)
    @Test
    public void testEditVehicleWithAdminAuth() throws Exception{
        VehiclesPage vehiclesPage=VehiclesPage.to(this.driver);
        LoginPage loginPage=LoginPage.openLogin(this.driver);
        vehiclesPage=LoginPage.doLogin(this.driver,loginPage,admin,admin);
        vehiclesPage=AddOrEditVehicle.editVehicle(this.driver,vehiclesPage.getEditButtons().get(0),"Audi","A3", String.valueOf(VehicleType.CAR),"32550.0");
        vehiclesPage=VehiclesPage.to(this.driver);
        vehiclesPage.assertElements(5,5,5,5,1);
    }

    @Order(9)
    @Test
    public void testVehiclePageWithUserAuth() throws Exception{
        VehiclesPage vehiclesPage=VehiclesPage.to(this.driver);
        LoginPage loginPage=LoginPage.openLogin(this.driver);
        loginPage=LoginPage.openLogin(this.driver);
        vehiclesPage=LoginPage.doLogin(this.driver,loginPage,user,user);
        vehiclesPage.assertElements(5,0,0,5,0);
    }

    @Order(10)
    @Test
    public void testBrandPageWithUserAuth() throws Exception{
        VehiclesPage vehiclesPage=VehiclesPage.to(this.driver);
        LoginPage loginPage=LoginPage.openLogin(this.driver);
        vehiclesPage=LoginPage.doLogin(this.driver,loginPage,user,user);
        BrandsPage brandsPage=BrandsPage.to(this.driver);
        brandsPage=BrandsPage.to(this.driver);
        brandsPage.assertElements(6,0);
    }

    @Order(12)
    @Test
    public void testRemoveFavoriteVehicleFromUser() throws Exception{
        VehiclesPage vehiclesPage=VehiclesPage.to(this.driver);
        LoginPage loginPage=LoginPage.openLogin(this.driver);
        vehiclesPage=LoginPage.doLogin(this.driver,loginPage,user,user);
        vehiclesPage=VehiclesPage.to(this.driver);
        vehiclesPage.getFavoriteButtons().get(0).click();
        Assert.assertEquals("url do not match","http://localhost:9999/favorite",this.driver.getCurrentUrl());
        FavoriteVehiclesPage favoriteVehiclesPage=FavoriteVehiclesPage.init(this.driver);
        favoriteVehiclesPage.assertElements(2,2);
        favoriteVehiclesPage.getRemoveButtons().get(0).click();
        favoriteVehiclesPage.assertElements(1,1);
    }

//    @Test
//    public void testFullUserScenario() throws Exception{
//
//        VehiclesPage vehiclesPage=VehiclesPage.to(this.driver);
//        vehiclesPage.assertElements(4,0,0,0,0);
//
//        vehiclesPage=VehiclesPage.to(this.driver);
//        vehiclesPage=vehiclesPage.filter(this.driver,"","","");
//        vehiclesPage.assertFilter(4);
//
//        vehiclesPage=vehiclesPage.filter(this.driver,"31000.0","","");
//        vehiclesPage.assertFilter(2);
//
//        vehiclesPage=vehiclesPage.filter(this.driver,"","CAR","");
//        vehiclesPage.assertFilter(3);
//
//        vehiclesPage=vehiclesPage.filter(this.driver,"","","A3");
//        vehiclesPage.assertFilter(1);
//
//        vehiclesPage=vehiclesPage.filter(this.driver,"32000.0","CAR","");
//        vehiclesPage.assertFilter(2);
//
//        vehiclesPage=vehiclesPage.filter(this.driver,"35000.0","","407");
//        vehiclesPage.assertFilter(1);
//
//        vehiclesPage=vehiclesPage.filter(this.driver,"","CAR","5");
//        vehiclesPage.assertFilter(1);
//
//        vehiclesPage=vehiclesPage.filter(this.driver,"35000.0","CAR","A3");
//        vehiclesPage.assertFilter(1);
//
//        LoginPage loginPage=LoginPage.openLogin(this.driver);
//
//        vehiclesPage=LoginPage.doLogin(this.driver,loginPage,admin,admin);
//        vehiclesPage.assertElements(4,4,4,4,1);
//
//        BrandsPage brandsPage=BrandsPage.to(this.driver);
//        brandsPage.assertElements(5,1);
//
//        brandsPage=AddBrand.addBrand(this.driver,"Ford");
//        brandsPage.assertElements(6,1);
//
//        vehiclesPage=AddOrEditVehicle.addVehicle(this.driver,"Ford","fiesta", String.valueOf(VehicleType.CAR),"4000.0");
//        vehiclesPage.assertElements(5,5,5,5,1);
//
//        vehiclesPage=AddOrEditVehicle.addVehicle(this.driver,vehicleBrand1.getName(),"golf", String.valueOf(VehicleType.CAR),"10000.0");
//        vehiclesPage.assertElements(6,6,6,6,1);
//        //
//        vehiclesPage.getDeleteButtons().get(1).click();
//        vehiclesPage.assertElements(5,5,5,5,1);
//
//        vehiclesPage=AddOrEditVehicle.editVehicle(this.driver,vehiclesPage.getEditButtons().get(0),"Audi","A3", String.valueOf(VehicleType.CAR),"32550.0");
//        vehiclesPage=VehiclesPage.to(this.driver);
//        vehiclesPage.assertElements(5,5,5,5,1);
//
//        loginPage=LoginPage.logout(this.driver);
//        loginPage=LoginPage.openLogin(this.driver);
//        vehiclesPage=LoginPage.doLogin(this.driver,loginPage,user,user);
//        vehiclesPage.assertElements(5,0,0,5,0);
//
//        brandsPage=BrandsPage.to(this.driver);
//        brandsPage.assertElements(6,0);
//
//        vehiclesPage=VehiclesPage.to(this.driver);
//        vehiclesPage.getFavoriteButtons().get(0).click();
//        Assert.assertEquals("url do not match","http://localhost:9999/favorite",this.driver.getCurrentUrl());
//
//        FavoriteVehiclesPage favoriteVehiclesPage=FavoriteVehiclesPage.init(this.driver);
//        favoriteVehiclesPage.assertElements(1,1);
//
//        favoriteVehiclesPage.getRemoveButtons().get(0).click();
//        favoriteVehiclesPage.assertElements(0,0);
//    }


}