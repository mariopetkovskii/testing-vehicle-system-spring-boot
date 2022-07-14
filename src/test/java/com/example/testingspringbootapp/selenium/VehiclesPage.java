package com.example.testingspringbootapp.selenium;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VehiclesPage extends AbstractPage {


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

    public void assertElements(int vehiclesNumber, int deleteButtons, int editButtons, int favoriteButtons, int addButtons){
        Assert.assertEquals("rows do not match", vehiclesNumber, this.getVehicleRows().size());
        Assert.assertEquals("delete do not match", deleteButtons, this.getDeleteButtons().size());
        Assert.assertEquals("edit do not match", editButtons, this.getEditButtons().size());
        Assert.assertEquals("favorite do not match", favoriteButtons, this.getFavoriteButtons().size());
        Assert.assertEquals("add is visible", addButtons, this.getAddVehicleButton().size());
    }
}