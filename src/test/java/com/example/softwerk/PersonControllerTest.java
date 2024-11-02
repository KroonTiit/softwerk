//Generated with ChatGPT fixed by Tiit Kroon
package com.example.softwerk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.softwerk.person.Person;
import com.example.softwerk.person.PersonController;
import com.example.softwerk.person.PersonRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class PersonControllerTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPerson_WithParents() {
        Person mother = new Person("Mother");
        Person father = new Person("Father");

        when(repository.findById(1L)).thenReturn(Optional.of(mother));
        when(repository.findById(2L)).thenReturn(Optional.of(father));

        List<Person> result = personController.addPerson("Child", 2L, 1L);

        verify(repository, times(1)).save(any(Person.class));
        assertNotNull(result);
    }

    @Test
    void testGetFather() {
        Person child = new Person("Child");
        Person father = new Person("Father");
        child.setFather(father);

        when(repository.findById(1L)).thenReturn(Optional.of(child));

        Person result = personController.getFather(1L);
        assertEquals("Father", result.getName());
    }

    @Test
    void testIsSibling() {
        Person person = new Person("Person");
        Person sibling = new Person("Sibling");

        Person father = new Person("Father");
        person.setFather(father);
        sibling.setFather(father);

        when(repository.findById(person.getId())).thenReturn(Optional.of(person));
        when(repository.findById(sibling.getId())).thenReturn(Optional.of(sibling));

        assertTrue(personController.isSibling(person.getId(), sibling.getId()));
    }

    @Test
    void testGetName() {
        Person person = new Person("TestName");
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        assertEquals("TestName", personController.getName(1L).get("name"));
    }
}