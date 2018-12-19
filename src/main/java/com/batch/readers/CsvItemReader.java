package com.batch.readers;

import com.person.Person;
import com.batch.readers.mapper.PersonFieldSetMapper;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

public class CsvItemReader extends FlatFileItemReader<Person> {

    public CsvItemReader() {
        setLinesToSkip(1);
        setResource(new ClassPathResource("/customer.csv"));
        setLineMapper(mapper());
    }

    private DefaultLineMapper mapper() {
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[] {"id", "firstName", "lastName", "birthDate"});

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new PersonFieldSetMapper());
        lineMapper.afterPropertiesSet();
        return lineMapper;
    }
}
