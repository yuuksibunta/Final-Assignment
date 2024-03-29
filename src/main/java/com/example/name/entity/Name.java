package com.example.name.entity;

import java.util.Objects;

public class Name {

    private Integer id;

    private String name;

    private int age;

    public Name(Integer id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return age == name.age && Objects.equals(id, name.id) && Objects.equals(this.name, name.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}
