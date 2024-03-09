package com.mpr.projekt.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DeleteDogPage {

    public static final String URL = "http://localhost:8081/deleteDog/1";
    WebDriver driver;

    @FindBy(id="submit-button")
    WebElement deleteButton;

    public DeleteDogPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(){
        driver.get(URL);
        PageFactory.initElements(driver, this);
    }

    public void clickDeleteButton() {
        deleteButton.click();
    }
}
