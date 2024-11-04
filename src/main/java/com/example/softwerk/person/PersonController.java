package com.example.softwerk.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.common.lang.Nullable;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@Transactional
@RestController
@RequestMapping("/api")
public class PersonController {
    @Autowired
    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/test")
    public String test() {
        return "Hello world";
    }

    @GetMapping("/getFamilytree")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @PostMapping("/addPerson")
    public List<Person> addPerson(@RequestParam String name, @Nullable Long idFather, @Nullable Long idMother) {
        return personService.addPerson(name, idFather, idMother);
    }

    @GetMapping("/getFather")
    public Person getFather(@RequestParam Long id) {
        return personService.getFather(id);
    }

   @GetMapping("/getMother")
    public Person getMother(@RequestParam Long id) {
        return personService.getMother(id);
    } 

    @GetMapping("/isSibling")
    public Boolean isSibling(@RequestParam Long id, Long siblingId) {
        return personService.isSibling(id, siblingId);
    }

    @GetMapping("/isSharingParent")
    public Boolean isSharingParent(@RequestParam Long id, Long siblingId) {
        return personService.isSharingParent(id, siblingId);
    }

    @GetMapping("/getName")
    public Map<String, String> getName(@RequestParam Long id) {
        return personService.getName(id);
    }
    
}