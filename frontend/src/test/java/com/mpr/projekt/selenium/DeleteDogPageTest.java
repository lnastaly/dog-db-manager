package com.mpr.projekt.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteDogPageTest {

    public static final String URL = "http://localhost:8081/deleteDog/1";
    WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void open() {
        DeleteDogPage deleteDogPage = new DeleteDogPage(driver);
        deleteDogPage.open();
        assertEquals(URL, driver.getCurrentUrl());
    }

    public void clickDeleteButton() {
        DeleteDogPage deleteDogPage = new DeleteDogPage(driver);
        deleteDogPage.open();
        deleteDogPage.clickDeleteButton();
        String currentUrl = driver.getCurrentUrl().split(";")[0];
        assertEquals("http://localhost:8081/dogs", currentUrl);
    }
}
