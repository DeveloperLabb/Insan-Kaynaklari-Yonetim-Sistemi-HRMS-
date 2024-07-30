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

    private final AuthenticationService authenticationService;

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, AuthenticationService authenticationService) {
        this.personRepository = personRepository;
        this.authenticationService = authenticationService;
    }

    public Optional<Person> savePerson(Person person) {
        person.setOwnerID(authenticationService.getCurrentUser().getId().toString());
        return Optional.of(personRepository.save(person));
    }

    public Optional<List<Person>> getAllPersons() {
        return Optional.of(personRepository.findByOwnerID(authenticationService.getCurrentUser().getId().toString()));
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
            person.setOwnerID(authenticationService.getCurrentUser().getId().toString());
            person.setId(id);
            return Optional.of(personRepository.save(person));
        }
        return Optional.empty();
    }
}
