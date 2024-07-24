package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true, name ="email")
    private String email;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(unique = true, nullable = false, name = "auth_id")
    private String oauth2Id;

    public User(Long id, String email, String name, String oauth2Id) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.oauth2Id = oauth2Id;
    }
    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOauth2Id() {
        return oauth2Id;
    }

    public void setOauth2Id(String oauth2Id) {
        this.oauth2Id = oauth2Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", oauth2Id='" + oauth2Id + '\'' +
                '}';
    }
}
