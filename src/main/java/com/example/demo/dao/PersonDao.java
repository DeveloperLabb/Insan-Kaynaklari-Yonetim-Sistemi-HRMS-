package com.example.demo.dao;

import com.example.demo.model.Person;

import java.util.UUID;

public interface PersonDao {
/*
DAO, veri erişim katmanı (Data Access Object) için kullanılan bir tasarım desenidir. Bu desen, uygulamanın veri katmanına erişimini soyutlamayı amaçlar. DAO, veri kaynağına (örneğin, bir veritabanına) erişimi yönetir ve bu sayede veri erişim kodunu uygulamanın iş mantığından ayırır.

DAO tasarım deseni, şu avantajları sağlar:

Soyutlama: Veritabanı erişim detaylarını soyutlayarak iş mantığından ayırır.
Tekrar Kullanılabilirlik: Aynı veri erişim kodu birden fazla yerde kullanılabilir.
Kolay Bakım: Veri erişim kodunun merkezi bir yerde toplanması, kodun bakımını ve yönetimini kolaylaştırır.
Bağımlılıkların Azaltılması: İş mantığı ve veri erişim kodu arasında gevşek bir bağlılık sağlar.
 */
    int insertPerson(UUID id, Person person);
    default int insertPerson(Person person) {
        UUID uid = UUID.randomUUID();
        return insertPerson(uid, person);
    }
}
