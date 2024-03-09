package com.mpr.projekt.repository;

import com.mpr.projekt.model.Dog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepository extends CrudRepository<Dog, Long> {
    List<Dog> findByName(String name);
    List<Dog> findByAge(int age);
}
