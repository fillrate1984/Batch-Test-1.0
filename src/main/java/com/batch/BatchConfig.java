package com.batch;

import com.batch.processors.MyItemProcessor;
import com.batch.readers.CsvItemReader;
import com.person.Person;
import org.hsqldb.TransactionManager;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
//@Import(DataSourceConfiguration.class)
public class BatchConfig {


    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    DataSource dataSource;


    @Bean
    ItemReader<Person> itemReader() {
        return new CsvItemReader();
    }

    @Bean
    ItemProcessor<Person, Person> itemProcessor() {
        return new MyItemProcessor();
    }

    /*@Bean
    ItemWriter<Person> itemWriter() {
        return items -> {
            for (Person person : items) {
                System.out.println(person);
            }
        };
    }*/


    @Bean
    ItemWriter<Person> itemWriter() {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<>();
        System.out.println("==================================================================================================");
        System.out.printf("--------------------------------------DataSource is: %s ------------------------------------------\n", dataSource);
        System.out.println("==================================================================================================");
        writer.setDataSource(dataSource);
        writer.setSql("INSERT INTO person (id, firstName, lastName, birthDay) VALUES (:id, :firstName, :lastName, :birthDate)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());

        return writer;
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Person, Person>chunk(10)
                .reader(itemReader())
                .writer(itemWriter())
                .processor(itemProcessor())
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

}
