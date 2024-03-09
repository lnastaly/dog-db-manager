package com.mpr.projekt.controller;

import com.mpr.projekt.exception.NoDogsFoundException;
import com.mpr.projekt.model.Dog;
import com.mpr.projekt.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class WebController {

    private final DogService service;

    @Autowired
    public WebController(DogService service) {
        this.service = service;
    }

    @GetMapping("/welcome")
    public String getWelcomeView() {
        return "welcome";
    }

    @GetMapping("/dogs")
    public String getAllDogs(Model model) {
        model.addAttribute("dogs", service.findAll());
        return "allDogs";
    }

    @GetMapping("/addDog")
    public String getAddView(Model model) {
        model.addAttribute("dog", new Dog(0L, "", 0));
        return "addDog";
    }

    public String validateDog(Dog dog) {
        if(dog.getName().isEmpty()) {
            return "Name cannot be empty!";
        }
        if(dog.getAge() < 0) {
            return "Age cannot be less than 0";
        }
        return null;
    }

    @PostMapping("/addDog")
    public String addDog(Dog dog, Model model, RedirectAttributes redirectAttributes) {
        String errorMessage = validateDog(dog);
        if(errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            return "addDog";
        }
        service.add(dog);
        redirectAttributes.addFlashAttribute("successMessage", "Dog added succesfully");
        return "redirect:/dogs";
    }

    @GetMapping("/editDog/{id}")
    public String getEditView(@PathVariable("id") Long id, Model model) {
        Dog dog = service.findById(id);
        if(dog != null) {
            model.addAttribute("dog", dog);
        } else {
            model.addAttribute("errorMessage", "Could not find dog");
        }
        return "editDog";
    }

    @PostMapping("/editDog/{id}")
    public String editDog(Dog dog, Model model, RedirectAttributes redirectAttributes, @PathVariable("id") Long id) {
        String errorMessage = validateDog(dog);
        if(errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            return "editDog";
        }
        dog.setId(id);
        service.update(dog);
        redirectAttributes.addFlashAttribute("successMessage", "Dog edited succesfully");
        return "redirect:/dogs";
    }

    @GetMapping("/deleteDog/{id}")
    public String getDeleteView(@PathVariable("id") Long id, Model model) {
        Dog dog = service.findById(id);
        if(dog != null) {
            model.addAttribute("dog", dog);
        } else {
            model.addAttribute("errorMessage", "Could not find dog");
        }
        return "deleteDog";
    }

    @PostMapping("/deleteDog/{id}")
    public String deleteDog(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        service.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Dog edited succesfully");
        return "redirect:/dogs";
    }

}
