package com.batch.processors;

import com.person.Person;
import org.springframework.batch.item.ItemProcessor;

import java.util.Date;

public class MyItemProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(Person person) {
        int id = person.getId();
        String firstName = person.getFirstName().toUpperCase();
        String lastName = person.getLastName().toUpperCase();
        Date birthDay = person.getBirthDate();
        Person processedPerson = new Person(id, firstName, lastName, birthDay);

        return processedPerson;
    }
}
