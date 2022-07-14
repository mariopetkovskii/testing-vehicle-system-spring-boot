package com.example.testingspringbootapp.selenium;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class FavoriteVehiclesPage extends AbstractPage{

    @FindBy(css = "tr[class=favorite-vehicle]")
    private List<WebElement> favoriteRows;

    @FindBy(css = ".remove-favorite-vehicle")
    private List<WebElement> removeButtons;

    public FavoriteVehiclesPage(WebDriver driver) {
        super(driver);
    }

    public static FavoriteVehiclesPage init(WebDriver driver){
        return PageFactory.initElements(driver,FavoriteVehiclesPage.class);
    }

    public void assertElements(int favoriteVehiclesNumber, int removeButtons){
        Assert.assertEquals("rows do not match",favoriteVehiclesNumber,this.getFavoriteRows().size());
        Assert.assertEquals("remove do not match",removeButtons,this.getRemoveButtons().size());
    }
}
