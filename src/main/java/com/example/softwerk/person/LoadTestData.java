package com.example.softwerk.person;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    private PersonRepository personRepository;

    @Bean
    @Transactional
    CommandLineRunner initDatabase() {
        Person god = new Person("God");
        Person mary = new Person("Mary");

        return args -> {
            log.info("Preloading " + personRepository.save(god));
            log.info("Preloading " + personRepository.save(mary));
            log.info("------------------Test Data Loaded------------------------");

        };
    }

}