package com.mpr.projekt.service;

import com.mpr.projekt.exception.*;
import com.mpr.projekt.model.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class DogService {

    public static final String BASE_URL = "http://localhost:8080";
    @Autowired
    RestClient restClient;

    public DogService(RestClient restClient) {
        this.restClient = restClient;
    }

    public Dog findById(Long id) {
        Dog dog = restClient
                .get()
                .uri(BASE_URL + "/dogs/" + id)
                .retrieve()
                .body(Dog.class);

        if(dog == null) {
            throw new DogNotFoundException("Dog not found for id: " + id);
        }
        return dog;
    }
    public List<Dog> findByName(String name) {
        List<Dog> dogs = restClient
                .get()
                .uri(BASE_URL + "/dogs/name/" + name)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if(dogs == null) {
            throw new NoDogsFoundException("No dogs named \"" + name + "\" found!");
        }
        return dogs;
    }
    public List<Dog> findByAge(int age) {
        List<Dog> dogs = restClient
                .get()
                .uri(BASE_URL + "/dogs/age/" + age)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if(dogs == null) {
            throw new NoDogsFoundException("No dogs aged " + age + " found!");
        }
        return dogs;
    }
    public List<Dog> findAll() {
        return restClient
                .get()
                .uri(BASE_URL + "/dogs")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public void add(Dog body) {
        if(body.getId() != null && body.getId() <= 0) {
            throw new InvalidDogIdException("ID has to be greater than 0!");
        }
        if(body.getId() != null && this.findById(body.getId()) != null) {
            throw new DogAlreadyExistsException("Dog with ID " + body.getId() + " already exists!");
        }
        if(body.getName().isEmpty()) {
            throw new InvalidDogNameException("Name cannot be empty!");
        }
        if(body.getAge() < 0) {
            throw new InvalidDogAgeException("Invalid age!");
        }
        restClient.post()
                .uri(BASE_URL + "/dogs")
                .body(body)
                .retrieve()
                .toBodilessEntity();
    }

    public void update(Dog body) {
        if(body.getId() == null) {
            throw new InvalidDogIdException("ID cannot be empty!");
        }
        if(body.getName().isEmpty()) {
            throw new InvalidDogNameException("Name cannot be empty!");
        }
        if(body.getAge() < 0) {
            throw new InvalidDogAgeException("Invalid age!");
        }

        restClient.put()
                .uri(BASE_URL + "/dogs")
                .body(body)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(Long id) {
        if(id == null) {
            throw new DogNotFoundException("Dog does not exist!");
        }
        restClient.delete()
                .uri(BASE_URL + "/dogs/delete/" + id)
                .retrieve()
                .toBodilessEntity();
    }

}
