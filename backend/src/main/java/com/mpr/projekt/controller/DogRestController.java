package com.mpr.projekt.controller;

import com.mpr.projekt.model.Dog;
import com.mpr.projekt.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class DogRestController {

    private final DogService service;
    private final Logger logger = Logger.getLogger("DogRestController");

    @Autowired
    public DogRestController(DogService service) {
        this.service = service;
    }

    @GetMapping("dogs")
    public ResponseEntity<List<Dog>> getAll() {
        logger.info("Endpoint called: getAll");
        if(this.service.findAll().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("dogs/{id}")
    public ResponseEntity<Optional<Dog>> getById(@PathVariable("id") Long id) {
        logger.info("Endpoint called: getById");
        if(this.service.findById(id).isPresent()) {
            return ResponseEntity.ok(this.service.findById(id));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("dogs/name/{name}")
    public ResponseEntity<List<Dog>> getByName(@PathVariable("name") String name) {
        logger.info("Endpoint called: getByName");
        if(this.service.findByName(name).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.service.findByName(name));
    }

    @GetMapping("dogs/age/{age}")
    public ResponseEntity<List<Dog>> getByAge(@PathVariable("age") int age) {
        logger.info("Endpoint called: getByAge");
        if(this.service.findByAge(age).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.service.findByAge(age));
    }

    @GetMapping("dogs/search/{name}")
    public ResponseEntity<List<Dog>> searchByName(@PathVariable("name") String name) {
        logger.info("Endpoint called: searchByName");
        List<Dog> matchingDogs = service.searchByName(name);

        if(!matchingDogs.isEmpty()) {
            return ResponseEntity.ok(matchingDogs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("dogs")
    public ResponseEntity<String> add(@RequestBody Dog body) {
        logger.info("Endpoint called: add");
        if(this.service.add(body) == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body("Dog added successfully");
    }

    @PutMapping("dogs")
    public ResponseEntity<String> update(@RequestBody Dog body) {
        logger.info("Endpoint called: update");
        if(this.service.update(body) == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body("Dog edited successfully");
    }

    @DeleteMapping("dogs/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        logger.info("Endpoint called: delete");
        if(this.service.findById(id).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        this.service.delete(id);
        return ResponseEntity.ok().body("Dog deleted successfully");
    }

    @DeleteMapping("dogs/deleteAllByName/{name}")
    public ResponseEntity<String> deleteAllByName(@PathVariable("name") String name) {
        logger.info("Endpoint called: deleteAllByName");
        if(this.service.findByName(name).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        this.service.deleteAllByName(name);
        return ResponseEntity.ok().body("Dogs deleted successfully");
    }

}
