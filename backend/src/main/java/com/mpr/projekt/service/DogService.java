package com.mpr.projekt.service;

import com.mpr.projekt.exception.*;
import com.mpr.projekt.model.Dog;
import com.mpr.projekt.repository.DogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DogService {
    DogRepository repository;

    public DogService(DogRepository repository) {
        this.repository = repository;
        this.repository.save(new Dog("Wiktor", 2));
        this.repository.save(new Dog("Nikodem", 3));
        this.repository.save(new Dog("Stasiek", 5));
    }

    public List<Dog> findAll() {
        List<Dog> dogs = (List<Dog>) this.repository.findAll();

        if(!dogs.isEmpty()) {
            return dogs;
        } else {
            throw new NoDogsFoundException("No dogs found!");
        }
    }

    public Optional<Dog> findById(Long id) {
        Optional<Dog> dog = this.repository.findById(id);

        if(dog.isPresent()) {
            return dog;
        } else {
            throw new DogNotFoundException("Dog not found for id: " + id);
        }
    }

    public List<Dog> findByName(String name) {
        List<Dog> matchingDogs = this.repository.findByName(name);

        if(!matchingDogs.isEmpty()) {
            return matchingDogs;
        } else {
            throw new NoDogsFoundException("No dogs named \"" + name + "\" found!");
        }
    }

    public List<Dog> findByAge(int age) {
        List<Dog> matchingDogs = this.repository.findByAge(age);

        if(!matchingDogs.isEmpty()) {
            return matchingDogs;
        } else {
            throw new NoDogsFoundException("No dogs aged " + age + " found!");
        }
    }

    public List<Dog> searchByName(String name) {
        List<Dog> allDogs = this.findAll();
        List<Dog> matchingDogs = new ArrayList<>();

        for(Dog dog : allDogs) {
            if(dog.getName().contains(name)) {
                matchingDogs.add(dog);
            }
        }

        if(!matchingDogs.isEmpty()) {
            return matchingDogs;
        } else {
            throw new NoDogsFoundException("No dogs named \"" + name + "\" found!");
        }
    }

    public Dog add(Dog body) {
        if(body.getId() != null && body.getId() <= 0) {
            throw new InvalidDogIdException("ID has to be greater than 0!");
        }
        if(body.getId() != null && this.repository.existsById(body.getId())) {
            throw new DogAlreadyExistsException("Dog with ID " + body.getId() + " already exists!");
        }
        if(body.getName().isEmpty()) {
            throw new InvalidDogNameException("Name cannot be empty!");
        }
        if(body.getAge() < 0) {
            throw new InvalidDogAgeException("Invalid age!");
        }

        return this.repository.save(body);
    }

    public void delete(Long id) {
        if(!this.repository.existsById(id)) {
            throw new DogNotFoundException("Dog with ID " + id + " does not exist!");
        }
        this.repository.deleteById(id);
    }

    public void deleteAllByName(String name) {
        List<Dog> dogsToDelete = searchByName(name);
        this.repository.deleteAll(dogsToDelete);
    }

    public Dog update(Dog body) {
        if(body.getId() == null) {
            throw new InvalidDogIdException("ID cannot be empty!");
        }
        if(!this.repository.existsById(body.getId())) {
            throw new DogNotFoundException("Dog with ID " + body.getId() + " does not exist!");
        }
        if(body.getName().isEmpty()) {
            throw new InvalidDogNameException("Name cannot be empty!");
        }
        if(body.getAge() < 0) {
            throw new InvalidDogAgeException("Invalid age!");
        }

        return this.repository.save(body);
    }

}
