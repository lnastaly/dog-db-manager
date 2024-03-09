package com.mpr.projekt.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EditDogPageTest {

    public static final String URL = "http://localhost:8081/editDog/1";
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
        EditDogPage editDogPage = new EditDogPage(driver);
        editDogPage.open();
        assertEquals(URL, driver.getCurrentUrl());
    }

    @Test
    public void fillInName() {
        EditDogPage editDogPage = new EditDogPage(driver);
        editDogPage.open();
        editDogPage.fillInName("name");
        assertEquals("name", editDogPage.dogName.getAttribute("value"));
    }

    @Test
    public void fillInAge() {
        EditDogPage editDogPage = new EditDogPage(driver);
        editDogPage.open();
        editDogPage.fillInAge("1");
        assertEquals("1", editDogPage.dogAge.getAttribute("value"));
    }

    @Test
    public void fillInAddDogForm() {
        EditDogPage editDogPage = new EditDogPage(driver);
        editDogPage.open();
        editDogPage.fillInName("name");
        editDogPage.fillInAge("1");
        editDogPage.clickSubmitButton();
        String currentUrl = driver.getCurrentUrl().split(";")[0];
        assertEquals("http://localhost:8081/dogs", currentUrl);
    }
}
