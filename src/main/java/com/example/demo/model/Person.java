package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    private String name;

    private String ownerID; // İsteğe bağlı olarak ekleyebilirsiniz

}
