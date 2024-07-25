package com.example.demo.api;

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
@RequestMapping("api/v1/person")
public class PersonController {
    private final PersonService personService;
    @Autowired
    public PersonController(@Qualifier("personService") PersonService personService) {//Dependency injection
        this.personService = personService;
    }

    @PostMapping("/addPerson")
    public ResponseEntity<Person> addPerson(@Valid @NonNull @RequestBody Person person) {
        Person savedPerson = personService.savePerson(person);
        return ResponseEntity.ok(savedPerson);
    }

    @GetMapping("/getAllPerson")//Aynı endpoint üzerinden farklı mapping işlemleri yapılabilir ancak aynı işlemler aynı endpointte yapılamaz.
    public List<Person> getAllPerson() {
        return personService.getAllPersons();
    }
    @DeleteMapping("/{id}")
    public Person deletePerson(@PathVariable("id") UUID id) {
        return personService.deletePerson(id);
    }
    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable("id") UUID id,@Valid @NonNull @RequestBody Person person) {
        return personService.updatePerson(id,person);
    }
    @GetMapping(path = "{id}")
    public Optional<Person> getPersonById(@PathVariable("id") UUID id) {
        return personService.getPersonById(id);
    }
}
