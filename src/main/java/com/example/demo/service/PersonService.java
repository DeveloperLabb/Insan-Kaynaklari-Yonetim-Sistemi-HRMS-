package com.example.demo.service;

import com.example.demo.dao.PersonDao;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("personService")
public class PersonService {
    private final PersonDao personDao;
    @Autowired
    public PersonService(@Qualifier("mySQLPerson") PersonDao personDao) {//Qualifier fakePersonDataAccess ile bağlı olduğu için böyle yaptık.
        //Ona da aynı adı verdik birden fazla database e bağlanması için.
        this.personDao = personDao;
    }

    public int addPerson(Person person) {
        return personDao.insertPerson(person);
    }
    public int addPerson(List<Person> persons) {
        for (Person person : persons) {
            addPerson(person);
        }
        return persons.size();
    }
    public int deletePerson(UUID id) {
        return personDao.deletePerson(id);
    }
    public int updatePerson(UUID id,Person person) {
        return personDao.updatePersonById(id,person);
    }
    public Person findPersonById(UUID id) {
        return personDao.findPersonById(id).orElse(null);
    }
    public List<Person> getAllPerson() {
        return personDao.getAllPerson();
    }
}
