package com.example.demo.api;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/person")
public class PersonController {
    private final PersonService personService;
    @Autowired
    public PersonController(@Qualifier("personService") PersonService personService) {
        this.personService = personService;
    }
    @PostMapping
    @RequestMapping("/addPerson")
    public int addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }
    @PostMapping
    @RequestMapping("/getAllPerson")
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }
}
