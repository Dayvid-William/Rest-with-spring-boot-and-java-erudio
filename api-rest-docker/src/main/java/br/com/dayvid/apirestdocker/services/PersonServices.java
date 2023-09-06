package br.com.dayvid.apirestdocker.services;

import br.com.dayvid.apirestdocker.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
@Service //Serve para o spring boot veja esse como objeto que sera injetado em run time em outras classes da aplicação
public class PersonServices {
    private static final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonServices.class.getName());
    
    public List<Person> findAll(){
        logger.info("Finding all people!");
        List<Person> persons = new ArrayList<>();
        for (int i  = 0; i < 8; i++ ) {
            Person person = mockPerson(i);
            persons.add(person);
        }
        return persons;
    }

    public Person findById(String id) {

        logger.info("Finding one person!");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Leandro");
        person.setLastName("Costa");
        person.setAddress("Uberlândia - Minas Gerais");
        person.setGender("Male");
        return person;
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Person name " + i);
        person.setLastName("Last name " + i);
        person.setAddress("Some address in Brazil " + i);
        person.setGender("Male");
        return person;
    }
}
