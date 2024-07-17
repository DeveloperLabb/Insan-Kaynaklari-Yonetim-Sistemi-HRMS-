package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;
//Database Section
public class Person {
    private final UUID id;
    private final String name;
    public Person(@JsonProperty("id") UUID id,@JsonProperty("name") String name) {//postmande json dosyası gönderirken algılaması için.
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
