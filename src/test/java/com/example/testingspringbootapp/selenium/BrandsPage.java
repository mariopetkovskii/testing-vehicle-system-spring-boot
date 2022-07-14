package com.example.testingspringbootapp.selenium;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class BrandsPage extends AbstractPage{

    @FindBy(css = "tr[class=brandd]")
    private List<WebElement> brandRows;

    @FindBy(css = ".add-brand-btn")
    private List<WebElement> addBrandButtons;

    public BrandsPage(WebDriver driver) {
        super(driver);
    }

    public static BrandsPage to(WebDriver driver){
        get(driver,"/brands");
        return PageFactory.initElements(driver,BrandsPage.class);
    }

    public void assertElements(int brandsNumber, int addButtons){
        Assert.assertEquals("brand rows do not match",brandsNumber,this.getBrandRows().size());
        Assert.assertEquals("add brand is visible",addButtons,this.getAddBrandButtons().size());
    }
}
