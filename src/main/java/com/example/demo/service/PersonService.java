package com.example.demo.service;

import com.example.demo.dao.PersonDao;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("personService")
public class PersonService {
    private final PersonDao personDao;
    @Autowired
    public PersonService(@Qualifier("fakeDao") PersonDao personDao) {//Qualifier fakePersonDataAccess ile bağlı olduğu için böyle yaptık.
        //Ona da aynı adı verdik birden fazla database e bağlanması için.
        this.personDao = personDao;
    }

    public int addPerson(Person person) {
        return personDao.insertPerson(person);
    }
    public List<Person> getAllPerson() {
        return personDao.getAllPerson();
    }
}
