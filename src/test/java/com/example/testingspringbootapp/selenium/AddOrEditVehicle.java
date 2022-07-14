package com.example.testingspringbootapp.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class AddOrEditVehicle extends AbstractPage{

    private WebElement brand;
    private WebElement model;
    private WebElement type;
    private WebElement price;
    private WebElement submit;

    public AddOrEditVehicle(WebDriver driver) {
        super(driver);
    }

    public static VehiclesPage addVehicle(WebDriver driver, String brand, String model, String type, String price){
        get(driver,"/vehicles/add");
        System.out.println(driver.getCurrentUrl());
        AddOrEditVehicle addOrEditVehicle= PageFactory.initElements(driver,AddOrEditVehicle.class);
        addOrEditVehicle.brand.click();
        addOrEditVehicle.brand.findElement(By.xpath("//option[. = '" + brand + "']")).click();
        addOrEditVehicle.model.sendKeys(model);
        addOrEditVehicle.type.click();
        addOrEditVehicle.type.findElement(By.xpath("//option[. = '" + type + "']")).click();
        addOrEditVehicle.price.sendKeys(price);

        addOrEditVehicle.submit.click();
        return PageFactory.initElements(driver,VehiclesPage.class);
    }

    public static VehiclesPage editVehicle(WebDriver driver,WebElement editButton, String brand, String model, String type, String price){
        editButton.click();
        System.out.println(driver.getCurrentUrl());
        AddOrEditVehicle addOrEditVehicle=PageFactory.initElements(driver,AddOrEditVehicle.class);
        addOrEditVehicle.brand.click();
        addOrEditVehicle.brand.findElement(By.xpath("//option[. = '" + brand + "']")).click();
        addOrEditVehicle.model.sendKeys(model);
        addOrEditVehicle.type.click();
        addOrEditVehicle.type.findElement(By.xpath("//option[. = '" + type + "']")).click();
        addOrEditVehicle.price.sendKeys(price);

        addOrEditVehicle.submit.click();
        return PageFactory.initElements(driver,VehiclesPage.class);
    }


}
