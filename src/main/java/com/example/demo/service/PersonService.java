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

    public Optional<Person> savePerson(Person person) {
        return Optional.of(personRepository.save(person));
    }

    public Optional<List<Person>> getAllPersons() {
        return Optional.of(personRepository.findAll());
    }

    public Optional<Person> getPersonById(UUID id) {
        return personRepository.findById(id);
    }

    public Optional<Person> deletePerson(UUID id) {
        Person person = personRepository.findById(id).orElse(null);
        personRepository.deleteById(id);
        return Optional.of(person);
    }

    public Optional<Person> updatePerson(UUID id, Person person) {
        if (personRepository.existsById(id)) {
            person.setId(id);
            return Optional.of(personRepository.save(person));
        }
        return Optional.empty();
    }
}
