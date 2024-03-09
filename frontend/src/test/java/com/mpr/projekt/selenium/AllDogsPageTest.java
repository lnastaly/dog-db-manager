package com.mpr.projekt.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AllDogsPageTest {

    public static final String URL = "http://localhost:8081/dogs";
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
        AllDogsPage allDogsPage = new AllDogsPage(driver);
        allDogsPage.open();
        assertEquals(URL, driver.getCurrentUrl());
    }

    @Test
    public void clickAddDogLink() {
        AllDogsPage allDogsPage = new AllDogsPage(driver);
        allDogsPage.open();
        allDogsPage.clickAddDogLink();
        assertEquals("http://localhost:8081/addDog", driver.getCurrentUrl());
    }

    @Test
    public void clickEditDogLink() {
        AllDogsPage allDogsPage = new AllDogsPage(driver);
        allDogsPage.open();
        allDogsPage.clickEditDogLink(1L);
        assertEquals("http://localhost:8081/editDog/1", driver.getCurrentUrl());
    }

    @Test
    public void clickDeleteDogLink() {
        AllDogsPage allDogsPage = new AllDogsPage(driver);
        allDogsPage.open();
        allDogsPage.clickDeleteDogLink(1L);
        assertEquals("http://localhost:8081/deleteDog/1", driver.getCurrentUrl());
    }
}
