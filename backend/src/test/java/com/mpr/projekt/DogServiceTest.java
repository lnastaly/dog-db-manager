package com.mpr.projekt;

import com.mpr.projekt.exception.*;
import com.mpr.projekt.model.Dog;
import com.mpr.projekt.repository.DogRepository;
import com.mpr.projekt.service.DogService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DogServiceTest {

    @Mock
    private DogRepository repository;
    private AutoCloseable openMocks;
    @InjectMocks
    private DogService service;

    @BeforeEach
    public void init(){
        openMocks = MockitoAnnotations.openMocks(this);
        service = new DogService(repository);
    }

    @AfterEach
    public void tearDown() throws Exception{
        openMocks.close();
    }

    @Test
    public void findAllFinds() {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("Wiktor", 2));
        dogs.add(new Dog("Nikodem", 3));
        dogs.add(new Dog("Stasiek", 5));

        when(repository.findAll()).thenReturn(dogs);
        List<Dog> result = service.findAll();

        assertEquals(dogs, result);
    }

    @Test
    public void findAllThrowsNoDogsFoundException() {
        Throwable exception = assertThrows(NoDogsFoundException.class, () -> service.findAll());

        assertEquals("No dogs found!", exception.getMessage());
    }

    @Test
    public void findByIdFinds() {
        Dog dog1 = new Dog("Wiktor", 2);
        Dog dog2 = new Dog("Nikodem", 3);
        Dog dog3 = new Dog("Stasiek", 5);

        when(repository.findById(1L)).thenReturn(Optional.of(dog1));
        Optional<Dog> result1 = service.findById(1L);
        when(repository.findById(2L)).thenReturn(Optional.of(dog2));
        Optional<Dog> result2 = service.findById(2L);
        when(repository.findById(3L)).thenReturn(Optional.of(dog3));
        Optional<Dog> result3 = service.findById(3L);

        assertEquals(Optional.of(dog1), result1);
        assertEquals(Optional.of(dog2), result2);
        assertEquals(Optional.of(dog3), result3);
    }

    @Test
    public void findByIdThrowsDogNotFoundException() {
        Long nonExistingId = 10L;

        Throwable exception = assertThrows(DogNotFoundException.class, ()-> service.findById(nonExistingId));
        assertEquals("Dog not found for id: " + nonExistingId, exception.getMessage());
    }

    @Test
    public void FindByNameFinds() {
        String nameA = "DogA";
        Dog dogA1 = new Dog(nameA, 2);
        Dog dogA2 = new Dog(nameA, 5);
        List<Dog> dogsNamedA = List.of(dogA1, dogA2);

        String nameB = "DogB";
        Dog dogB = new Dog(nameB, 4);
        List<Dog> dogsNamedB = List.of(dogB);

        when(repository.findByName(nameA)).thenReturn(dogsNamedA);
        List<Dog> foundDogsA = service.findByName(nameA);
        when(repository.findByName(nameB)).thenReturn(dogsNamedB);
        List<Dog> foundDogsB = service.findByName(nameB);

        assertEquals(2, foundDogsA.size());
        assertEquals(dogsNamedA, foundDogsA);
        assertEquals(1, foundDogsB.size());
        assertEquals(dogsNamedB, foundDogsB);
    }

    @Test
    public void FindByNameThrowsNoDogsFoundException() {
        String name = "name";
        Throwable exception = assertThrows(NoDogsFoundException.class, () -> service.findByName(name));

        assertEquals("No dogs named \"" + name + "\" found!", exception.getMessage());
    }

    @Test
    public void FindByAgeFinds() {
        int age = 1;
        Dog dog1 = new Dog("A", age);
        Dog dog2 = new Dog("B", age);
        Dog dog3 = new Dog("C", age);
        List<Dog> dogsAged1 = List.of(dog1, dog2, dog3);

        int age2 = 2;
        Dog dog4 = new Dog("D", age2);
        Dog dog5 = new Dog("E", age2);
        List<Dog> dogsAged2 = List.of(dog4, dog5);

        when(repository.findByAge(age)).thenReturn(dogsAged1);
        List<Dog> foundDogs1 = service.findByAge(age);
        when(repository.findByAge(age2)).thenReturn(dogsAged2);
        List<Dog> foundDogs2 = service.findByAge(age2);

        assertEquals(3, foundDogs1.size());
        assertEquals(dogsAged1, foundDogs1);
        assertEquals(2, foundDogs2.size());
        assertEquals(dogsAged2, foundDogs2);
    }

    @Test
    public void FindByAgeThrowsNoDogsFoundException() {
        int age = 2;
        Throwable exception = assertThrows(NoDogsFoundException.class, () -> service.findByAge(age));

        assertEquals("No dogs aged " + age + " found!", exception.getMessage());
    }

    @Test
    public void searchFinds() {
        Dog dog1 = new Dog("Wiktor", 2);
        Dog dog2 = new Dog("Nikodem", 3);
        Dog dog3 = new Dog("Stasiek", 5);
        List<Dog> dogs = Arrays.asList(dog1, dog2, dog3);

        when(repository.findAll()).thenReturn(dogs);

        List<Dog> dogsToFind = Arrays.asList(dog1, dog2);
        List<Dog> foundDogs = service.searchByName("o");

        List<Dog> dogsToFind2 = Arrays.asList(dog3);
        List<Dog> foundDogs2 = service.searchByName("a");

        assertEquals(foundDogs, dogsToFind);
        assertEquals(foundDogs2, dogsToFind2);
    }

    @Test
    public void searchThrowsNoDogsFoundException() {
        Dog dog1 = new Dog("Wiktor", 2);
        Dog dog2 = new Dog("Nikodem", 3);
        Dog dog3 = new Dog("Stasiek", 5);
        List<Dog> dogs = Arrays.asList(dog1, dog2, dog3);

        when(repository.findAll()).thenReturn(dogs);

        String name = "capybara";
        Throwable exception = assertThrows(NoDogsFoundException.class, () -> service.searchByName(name));

        assertEquals("No dogs named \"" + name + "\" found!", exception.getMessage());
    }

    @Test
    public void addAdds() {
        Dog dog = new Dog("Wiktor", 2);
        ArgumentCaptor<Dog> captor = ArgumentCaptor.forClass(Dog.class);
        when(repository.save(captor.capture())).thenReturn(dog);
        service.add(dog);
        Mockito.verify(repository, Mockito.times(1)).save(dog);
        Dog dogFromSaveCall = captor.getValue();
        assertEquals(dog, dogFromSaveCall);
    }

    @Test
    public void addThrowsInvalidDogIdException() {
        Long invalidId = -2L;
        Dog dog = new Dog(invalidId, "Wiktor", 2);
        Throwable exception = assertThrows(InvalidDogIdException.class, () -> service.add(dog));
        assertEquals("ID has to be greater than 0!", exception.getMessage());
    }

    @Test
    public void addThrowsDogAlreadyExistsException() {
        Long existingId = 1L;
        Dog existingDog = new Dog(existingId, "Wiktor", 2);
        when(repository.existsById(existingId)).thenReturn(true);
        assertThrows(DogAlreadyExistsException.class, () -> service.add(existingDog));
    }

    @Test
    public void addThrowsInvalidDogNameException() {
        String invalidName = "";
        Dog dog = new Dog(invalidName, 2);
        Throwable exception = assertThrows(InvalidDogNameException.class, () -> service.add(dog));
        assertEquals("Name cannot be empty!", exception.getMessage());
    }

    @Test
    public void addThrowsInvalidDogAgeException() {
        int invalidAge = -1;
        Dog dog = new Dog("Wiktor", invalidAge);
        Throwable exception = assertThrows(InvalidDogAgeException.class, () -> service.add(dog));
        assertEquals("Invalid age!", exception.getMessage());
    }

    @Test
    public void updateUpdates() {
        Dog dog = new Dog(1L, "Wiktor", 6);
        when(repository.existsById(1L)).thenReturn(true);
        service.update(dog);
        Mockito.verify(repository).save(dog);
    }

    @Test
    public void updateThrowsInvalidDogIdException() {
        Long invalidId = null;
        Dog dog = new Dog(invalidId, "Wiktor", 2);
        Throwable exception = assertThrows(InvalidDogIdException.class, () -> service.update(dog));
        assertEquals("ID cannot be empty!", exception.getMessage());
    }

    @Test
    public void updateThrowsInvalidDogNameException() {
        String invalidName = "";
        Dog dog = new Dog(1L, invalidName, 2);
        when(repository.existsById(1L)).thenReturn(true);
        Throwable exception = assertThrows(InvalidDogNameException.class, () -> service.update(dog));
        assertEquals("Name cannot be empty!", exception.getMessage());
    }

    @Test
    public void updateThrowsInvalidDogAgeException() {
        int invalidAge = -1;
        Dog dog = new Dog(1L, "Wiktor", invalidAge);
        when(repository.existsById(1L)).thenReturn(true);
        Throwable exception = assertThrows(InvalidDogAgeException.class, () -> service.update(dog));
        assertEquals("Invalid age!", exception.getMessage());
    }

    @Test
    public void deleteDeletes() {
        when(repository.existsById(1L)).thenReturn(true);
        service.delete(1L);
        Mockito.verify(repository).deleteById(1L);
    }

    @Test
    public void deleteThrowsDogNotFoundException() {
        Long dogId = 1L;
        Throwable exception = assertThrows(DogNotFoundException.class, () -> service.delete(dogId));
        assertEquals("Dog with ID " + dogId + " does not exist!", exception.getMessage());
    }

}
