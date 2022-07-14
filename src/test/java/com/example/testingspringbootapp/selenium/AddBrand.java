package com.example.testingspringbootapp.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class AddBrand extends AbstractPage{

    private WebElement name;

    private WebElement submit;

    public AddBrand(WebDriver driver) {
        super(driver);
    }

    public static BrandsPage addBrand(WebDriver driver, String name){
        get(driver,"/brands/add");
        System.out.println(driver.getCurrentUrl());
        AddBrand addBrand= PageFactory.initElements(driver,AddBrand.class);
        addBrand.name.sendKeys(name);

        addBrand.submit.click();
        return PageFactory.initElements(driver,BrandsPage.class);
    }
}
