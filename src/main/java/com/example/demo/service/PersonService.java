package com.example.demo.service;

import com.example.demo.repository.PersonRepository;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(UUID id) {
        return personRepository.findById(id);
    }

    public Person deletePerson(UUID id) {
        Person person = personRepository.findById(id).orElse(null);
        personRepository.deleteById(id);
        return person;
    }

    public Person updatePerson(UUID id, Person person) {
        if (personRepository.existsById(id)) {
            person.setId(id);
            return personRepository.save(person);
        }
        return null;
    }
}
