package com.mpr.projekt;

import com.mpr.projekt.model.Dog;
import com.mpr.projekt.service.DogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.MockServerRestClientCustomizer;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@org.springframework.boot.test.autoconfigure.web.client.RestClientTest
public class RestClientTest {

    MockServerRestClientCustomizer customizer = new MockServerRestClientCustomizer();
    RestClient.Builder builder = RestClient.builder();

    DogService service;

    @BeforeEach
    public void setUp() {
        customizer.customize(builder);
        service = new DogService(builder.build());
    }

    @Test
    public void testGetAllDogs() {
        customizer.getServer().expect(MockRestRequestMatchers.requestTo("http://localhost:8080/dogs"))
                .andRespond(MockRestResponseCreators.withSuccess("""
                        [{"id":1,"name":"Wiktor","age":2},
                        {"id":2,"name":"Nikodem","age":3},
                        {"id":3,"name":"Stasiek","age":5}]
                        """, MediaType.APPLICATION_JSON));

        List<Dog> foundDogs = service.findAll();
        Long id = 1L;
        for(Dog foundDog : foundDogs) {
            assertEquals(id, foundDog.getId());
            id++;
        }
    }

}
