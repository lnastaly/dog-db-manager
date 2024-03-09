package com.mpr.projekt;

import com.mpr.projekt.model.Dog;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyRestAssuredControllerTest {
    private static final String URI = "http://localhost:8080";

    @BeforeAll
    public void setUp() {
        with()
                .body(new Dog("dog1", 1))
                .contentType("application/json")
                .post(URI + "/dog/add");
    }

    @AfterAll
    public void tearDown() {
        with()
                .body(new Dog("dog1", 1))
                .contentType("application/json")
                .delete(URI + "/dogs/deleteAllByName/dog1");
    }

    @Test
    public void testGetDog() {
        when()
                .get(URI + "/dogs/1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(1))
                .body("name", equalTo("Wiktor"))
                .log()
                .body();
    }

    @Test
    public void testGetDog2() {
        Dog dog = when()
                .get(URI + "/dogs/1")
                .then()
                .statusCode(200)
                .extract()
                .as(Dog.class);

        assertEquals(1, dog.getId());
        assertEquals("Wiktor", dog.getName());
    }

    @Test
    public void testPostDog() {
        with()
                .body(new Dog("dog1", 2))
                .contentType("application/json")
                .post(URI + "/dogs")
                .then()
                .assertThat()
                .statusCode(200)
                .log()
                .body();
    }

    @Test
    public void testPutDog() {
        with()
                .body(new Dog("Wiktor", 2).setId(1L))
                .contentType("application/json")
                .put(URI + "/dogs")
                .then()
                .statusCode(200)
                .log()
                .body();
    }

    @Test
    public void testDeleteDog() {
        with()
                .body(new Dog("Wiktor", 2).setId(1L))
                .contentType("application/json")
                .delete(URI + "/dogs/delete/1")
                .then()
                .statusCode(200)
                .log()
                .body();
    }
}
