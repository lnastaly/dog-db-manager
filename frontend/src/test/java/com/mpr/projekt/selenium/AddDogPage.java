package com.mpr.projekt.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddDogPage {

    public static final String URL = "http://localhost:8081/addDog";
    WebDriver driver;

    @FindBy(name="name")
    WebElement dogName;
    @FindBy(name="age")
    WebElement dogAge;
    @FindBy(id="submit-button")
    WebElement submitButton;

    public AddDogPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(){
        driver.get(URL);
        PageFactory.initElements(driver, this);
    }

    public void fillInName(String name) {
        this.dogName.sendKeys(name);
    }

    public void fillInAge(String age) {
        this.dogAge.clear();
        this.dogAge.sendKeys(age);
    }

    public void clickSubmitButton() {
        submitButton.click();
    }


}
