package com.mpr.projekt.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AllDogsPage {

    public static final String URL = "http://localhost:8081/dogs";
    WebDriver driver;

    @FindBy(id = "add-link")
    WebElement addDogLink;

    public AllDogsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(){
        driver.get(URL);
        PageFactory.initElements(driver, this);
    }

    public void clickAddDogLink() {
        addDogLink.click();
    }

    public void clickEditDogLink(Long dogId) {
        WebElement editLink = driver.findElement(By.id("edit-link-" + dogId));
        editLink.click();
    }

    public void clickDeleteDogLink(Long dogId) {
        WebElement deleteLink = driver.findElement(By.id("delete-link-" + dogId));
        deleteLink.click();
    }
}
