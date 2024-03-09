package com.mpr.projekt.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddDogPageTest {

    public static final String URL = "http://localhost:8081/addDog";
    WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
    }

    @AfterAll
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void open() {
        AddDogPage addDogPage = new AddDogPage(driver);
        addDogPage.open();
        assertEquals(URL, driver.getCurrentUrl());
    }

    @Test
    public void fillInName() {
        AddDogPage addDogPage = new AddDogPage(driver);
        addDogPage.open();
        addDogPage.fillInName("name");
        assertEquals("name", addDogPage.dogName.getAttribute("value"));
    }

    @Test
    public void fillInAge() {
        AddDogPage addDogPage = new AddDogPage(driver);
        addDogPage.open();
        addDogPage.fillInAge("1");
        assertEquals("1", addDogPage.dogAge.getAttribute("value"));
    }

    @Test
    public void fillInAddDogForm() {
        AddDogPage addDogPage = new AddDogPage(driver);
        addDogPage.open();
        addDogPage.fillInName("name");
        addDogPage.fillInAge("1");
        addDogPage.clickSubmitButton();
        String currentUrl = driver.getCurrentUrl().split(";")[0];
        assertEquals("http://localhost:8081/dogs", currentUrl);
    }
}
