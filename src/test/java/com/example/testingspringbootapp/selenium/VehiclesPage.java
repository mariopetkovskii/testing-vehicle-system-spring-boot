package com.example.testingspringbootapp.selenium;

import lombok.Getter;
import org.openqa.selenium.support.ui.Select;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VehiclesPage extends AbstractPage {

    private WebElement price;

    private WebElement type;

    private WebElement containsModel;

    private WebElement filter;

    @FindBy(css = "tr[class=vehicle]")
    private List<WebElement> vehicleRows;


    @FindBy(css = ".delete-vehicle")
    private List<WebElement> deleteButtons;


    @FindBy(css = ".edit-vehicle")
    private List<WebElement> editButtons;


    @FindBy(css = ".favorite-vehicle")
    private List<WebElement> favoriteButtons;


    @FindBy(css = ".add-vehicle-btn")
    private List<WebElement> addVehicleButton;

    public VehiclesPage(WebDriver driver) {
        super(driver);
    }

    public static VehiclesPage to(WebDriver driver){
        get(driver,"/vehicles");
        return PageFactory.initElements(driver,VehiclesPage.class);
    }

    public VehiclesPage filter(WebDriver driver,String price, String type, String containsModel){
        this.price.sendKeys(price);
        Select select=new Select(this.type);
        select.selectByValue(type);
        this.filter.click();
        get(driver,"/vehicles?price=" + price +"&type=" + type + "&containsModel=" + containsModel);
        System.out.println(driver.getCurrentUrl());
        return PageFactory.initElements(driver,VehiclesPage.class);
    }

    public void assertElements(int vehiclesNumber, int deleteButtons, int editButtons, int favoriteButtons, int addButtons){
        Assert.assertEquals("vehicles rows do not match", vehiclesNumber, this.getVehicleRows().size());
        Assert.assertEquals("vehicles delete do not match", deleteButtons, this.getDeleteButtons().size());
        Assert.assertEquals("vehicles edit do not match", editButtons, this.getEditButtons().size());
        Assert.assertEquals("vehicles favorite do not match", favoriteButtons, this.getFavoriteButtons().size());
        Assert.assertEquals("add vehicle is visible", addButtons, this.getAddVehicleButton().size());
    }

    public void assertFilter(int vehiclesNumber){
        Assert.assertEquals("Number of items",vehiclesNumber,this.getVehicleRows().size());
    }


}