package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class PersonController {
    private final PersonService personService;
    @Autowired
    public PersonController(@Qualifier("personService") PersonService personService) {//Dependency injection
        this.personService = personService;
    }

    @PostMapping("/addPerson")
    public ResponseEntity<Person> addPerson(@Valid @NonNull @RequestBody Person person) {
        Optional<Person> savedPerson = personService.savePerson(person);
        return savedPerson.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("dashboard/getAllPerson")//Aynı endpoint üzerinden farklı mapping işlemleri yapılabilir ancak aynı işlemler aynı endpointte yapılamaz.
    public ResponseEntity<List<Person>> getAllPerson() {
        Optional<List<Person>> personList = personService.getAllPersons();
        if (personList.isPresent()) {
            return ResponseEntity.ok(personList.get());
        }
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable("id") UUID id) {
        Optional<Person> deletedPerson = personService.deletePerson(id);
        return deletedPerson.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("update/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") UUID id, @Valid @NonNull @RequestBody Person person) {
        Optional<Person> updatedPerson = personService.updatePerson(id, person);
        return updatedPerson.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping(path = "{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") UUID id) {
        Optional<Person> person = personService.getPersonById(id);
        return person.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
