package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")//Qualifier fakePersonDataAccess ile bağlı olduğu için böyle yaptık.
//Ona da aynı adı verdik birden fazla database e bağlanması için.
public class FakePersonDataAccessService implements PersonDao {
    private static List<Person> DB = new ArrayList<Person>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> getAllPerson() {
        return DB;
    }

    @Override
    public int deletePerson(UUID id) {
        Optional<Person> personOptional=findPersonById(id);
        if(personOptional.isPresent()) {
            DB.remove(personOptional.get());
            return 1;
        }
        return 0;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        Person foundPerson;
        if(findPersonById(id).isPresent()){
            foundPerson=findPersonById(id).get();
            DB.remove(foundPerson);
            insertPerson(person);
            return 1;
        }
        return 0;
    }

    @Override
    public Optional<Person> findPersonById(UUID id) {
        // Burada bir Person nesnesi bulma işlemi yapılır.
        // Eğer kişi bulunursa Optional.of(person) ile geri dönebiliriz.
        // Eğer kişi bulunamazsa Optional.empty() ile boş Optional dönebiliriz.
        // Bu şekilde null check yapmaktan kaçınmış oluruz.
        return DB.stream().filter(person -> person.getId().equals(id)).findFirst();
        /*
        DB.stream(): DB adı verilen bir koleksiyon veya veritabanı üzerinde stream() metodu çağrılarak bir akış oluşturulur. Bu akış, koleksiyon veya veritabanındaki tüm elemanları işlemek için bir veri akışıdır.

filter(person -> person.getId().equals(id)): filter() metoduyla akış üzerinde bir filtreleme işlemi yapılır. Burada lambda ifadesi (person -> person.getId().equals(id)) kullanılarak her person nesnesi için id alanı id parametresiyle eşleşenleri filtreler.

findFirst(): Filtrelenmiş akış üzerinde findFirst() metodu çağrılarak ilk eleman seçilir. Eğer filtrelenmiş akış boş ise, Optional.empty() döner. Eğer bir eleman bulunursa, bu elemanı içeren bir Optional nesnesi döner.
         */

        /*
        Optional<Person> personOptional = findPersonById("12345");
if (personOptional.isPresent()) {
    Person person = personOptional.get();
    // person nesnesi burada kullanılabilir
} else {
    // personOptional içindeki değer null ise buraya düşer
}
         */
    }

    public static List<Person> getDB() {
        return DB;
    }

}
