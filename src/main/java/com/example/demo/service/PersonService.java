package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.PersonRepository;
import com.example.demo.model.Person;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    private final AuthenticationService authenticationService;

    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, AuthenticationService authenticationService, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    public Optional<Person> savePerson(Person person) {
        person.setOwnerID(authenticationService.getCurrentUser().getId().toString());
        return Optional.of(personRepository.save(person));
    }

    public Optional<List<Person>> getAllPersons() {

        if(authenticationService.getCurrentUser().getRole().toString().equals("SYSTEM_ADMIN")) {
            List<Person> allPersonsWithOwnerUsername = personRepository.findAll();
            if(Optional.of(allPersonsWithOwnerUsername).isPresent()){
                for(Person person : allPersonsWithOwnerUsername){
                    String currUsername = userRepository.findById(Long.parseLong(person.getOwnerID())).get().getUsername();
                    person.setOwnerID(currUsername);
                }
            }
            return Optional.of(allPersonsWithOwnerUsername);
        }
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
            if(authenticationService.getCurrentUser().getRole().toString().equals("SYSTEM_ADMIN")) {
                person.setOwnerID(personRepository.findById(id).get().getOwnerID());
            }else{
                person.setOwnerID(authenticationService.getCurrentUser().getId().toString());
            }
            person.setId(id);
            return Optional.of(personRepository.save(person));
        }
        return Optional.empty();
    }
}
