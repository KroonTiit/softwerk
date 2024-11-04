package com.example.softwerk.person;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.softwerk.PersonNotFoundException;

import io.micrometer.common.lang.Nullable;

@Service
public class PersonService {
    private final PersonRepository repository;

    public PersonService( PersonRepository repository) {
        this.repository = repository;
    }

    public List<Person> getAllPersons(){
        return repository.findAll();
    }

    public List<Person> addPerson(String name, @Nullable Long idFather, @Nullable Long idMother){
        Person person = new Person(name);

        if (idMother != null) {
            Person mother = findPerson(idMother);
            person.setMother(mother);
            mother.getMotherToChildren().add(person);
        }
        if (idFather != null) {
            Person father = findPerson(idFather);
            person.setFather(father);
            father.getFatherToChildren().add(person);
        }
        repository.save(person);
        return repository.findAll();
    }

    public Person getFather(@RequestParam Long id) {
        return findPerson(id).getFather();
    }

    public Person getMother(@RequestParam Long id) {
        return findPerson(id).getMother();
    } 

    public Boolean isSibling(@RequestParam Long id, Long siblingId) {
        Person person = findPerson(id);
        Person sibling = findPerson(siblingId);

        return person.getFather() == sibling.getFather() &&
                person.getMother() == sibling.getMother();
    }

    public Boolean isSharingParent(@RequestParam Long id, Long siblingId) {
        Person person = findPerson(id);
        Person sibling = findPerson(siblingId);

        return person.getFather() == sibling.getFather() || 
                person.getMother() == sibling.getMother();
    }

    public Map<String, String> getName(@RequestParam Long id) {
        return Collections.singletonMap("name", findPerson(id).getName());
    }

    public Person findPerson(Long id){
        return repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

}
