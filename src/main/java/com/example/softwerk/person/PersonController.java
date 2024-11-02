package com.example.softwerk.person;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.softwerk.PersonNotFoundException;

import io.micrometer.common.lang.Nullable;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@Transactional
@RestController
@RequestMapping("/api")
public class PersonController {
    private final PersonRepository repository;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/test")
    public String test() {
        return "Hello world";
    }

    @GetMapping("/getFamilytree")
    public List<Person> getAllPersons() {
        return repository.findAll();
    }

    @PostMapping("/addPerson")
    public List<Person> addPerson(@RequestParam String name, @Nullable Long idFather, @Nullable Long idMother) {
        Person person = new Person(name);
        
        if (idMother != null) {
            Person mother = repository.findById(idMother).orElseThrow(() -> new PersonNotFoundException(idMother));
            person.setMother(mother);
            mother.getMotherToChildren().add(person);
        }
        if (idFather != null) {
            Person father = repository.findById(idFather).orElseThrow(() -> new PersonNotFoundException(idFather));
            person.setFather(father);
            father.getFatherToChildren().add(person);
        }
        repository.save(person);
        return repository.findAll();
    }

    @GetMapping("/getFather")
    public Person getFather(@RequestParam Long id) {
        Person person = repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        return person.getFather();
    }

   @GetMapping("/getMother")
    public Person getMother(@RequestParam Long id) {
        Person person = repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        return person.getMother();
    } 

    @GetMapping("/isSibling")
    public Boolean isSibling(@RequestParam Long id, Long siblingId) {
        Person person = repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        Person sibling = repository.findById(siblingId).orElseThrow(() -> new PersonNotFoundException(siblingId));

        return person.getFather() == sibling.getFather() &&
                person.getMother() == sibling.getMother();
    }

    @GetMapping("/isSharingParent")
    public Boolean isSharingParent(@RequestParam Long id, Long siblingId) {
        Person person = repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        Person sibling = repository.findById(siblingId).orElseThrow(() -> new PersonNotFoundException(siblingId));

        return person.getFather() == sibling.getFather() || 
                person.getMother() == sibling.getMother();
    }

    @GetMapping("/getName")
    public Map<String, String> getName(@RequestParam Long id) {
        Person person = repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        return Collections.singletonMap("name", person.getName());
    }
}