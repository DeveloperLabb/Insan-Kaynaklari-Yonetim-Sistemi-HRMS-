package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.UUID;
//Database Section
public class Person {
    private final UUID id;
    @NotBlank
    private String name;
    private String ownerID;//No implementation yet.
    public Person(@JsonProperty("id") UUID id,@JsonProperty("name") String name) {//postmande json dosyası gönderirken algılaması için.
        this.id = id;
        this.name = name;
    }
    public Person(String id,String name) {//postmande json dosyası gönderirken algılaması için.
        this.id = UUID.fromString(id);
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
