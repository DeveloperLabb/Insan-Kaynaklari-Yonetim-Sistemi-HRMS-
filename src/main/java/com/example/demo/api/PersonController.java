package com.example.demo.api;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/person")
public class PersonController {
    private final PersonService personService;
    @Autowired
    public PersonController(@Qualifier("personService") PersonService personService) {//Dependency injection
        this.personService = personService;
    }
    @PostMapping("/addPerson")
    public int addPerson(@Valid @NonNull @RequestBody Person person) {
        return personService.addPerson(person);
    }
    @PostMapping("/addPersons")
    public int addPerson(@Valid @NonNull @RequestBody List<Person> persons) {
        return personService.addPerson(persons);
    }
    @GetMapping("/getAllPerson")//Aynı endpoint üzerinden farklı mapping işlemleri yapılabilir ancak aynı işlemler aynı endpointte yapılamaz.
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }
    @DeleteMapping("/{id}")
    public int deletePerson(@PathVariable("id") UUID id) {
        return personService.deletePerson(id);
    }
    @PutMapping("/{id}")
    public int updatePerson(@PathVariable("id") UUID id,@Valid @NonNull @RequestBody Person person) {
        return personService.updatePerson(id,person);
    }
    @GetMapping(path = "{id}")
    public Person getPersonById(@PathVariable("id") UUID id) {
        return personService.findPersonById(id);
    }
}
