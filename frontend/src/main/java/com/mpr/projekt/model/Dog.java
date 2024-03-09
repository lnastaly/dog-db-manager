package com.mpr.projekt.model;

public class Dog {

    private Long id;
    private String name;
    private int age;

    protected Dog(){}

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Dog(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public Dog setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}