package com.batch.readers.mapper;

import com.person.Person;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class PersonFieldSetMapper implements FieldSetMapper<Person> {
    @Override
    public Person mapFieldSet(FieldSet fieldSet) throws BindException {
        return new Person(fieldSet.readInt("id"),
        fieldSet.readString("firstName"),
        fieldSet.readString("lastName"),
        fieldSet.readDate("birthDate"));
    }
}
