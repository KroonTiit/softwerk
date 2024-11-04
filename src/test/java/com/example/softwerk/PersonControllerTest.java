//Generated with ChatGPT fixed by Tiit Kroon
package com.example.softwerk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.softwerk.person.Person;
import com.example.softwerk.person.PersonController;
import com.example.softwerk.person.PersonService;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class PersonControllerTest {
    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHelloWorld() {
        String result = personController.test();
        assertEquals("Hello world", result);
    }

    @Test
    void testGetAllPersons() {
        List<Person> personList = List.of(new Person("Alice"), new Person("Bob"));
        when(personService.getAllPersons()).thenReturn(personList);

        List<Person> result = personController.getAllPersons();
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void testAddPerson() {
        Person child = new Person("Child");
        List<Person> updatedList = List.of(child);
        when(personService.addPerson("Child", null, null)).thenReturn(updatedList);

        List<Person> result = personController.addPerson("Child", null, null);
        assertEquals(1, result.size());
        assertEquals("Child", result.get(0).getName());
    }

    @Test
    void testGetFather() {
        Person father = new Person("Father");
        when(personService.getFather(1L)).thenReturn(father);

        Person result = personController.getFather(1L);
        assertEquals("Father", result.getName());
    }

    @Test
    void testGetMother() {
        Person mother = new Person("Mother");
        when(personService.getMother(1L)).thenReturn(mother);

        Person result = personController.getMother(1L);
        assertEquals("Mother", result.getName());
    }

    @Test
    void testIsSibling() {
        when(personService.isSibling(1L, 2L)).thenReturn(true);

        Boolean result = personController.isSibling(1L, 2L);
        assertTrue(result);
    }

    @Test
    void testIsSharingParent() {
        when(personService.isSharingParent(1L, 2L)).thenReturn(true);

        Boolean result = personController.isSharingParent(1L, 2L);
        assertTrue(result);
    }

    @Test
    void testGetName() {
        when(personService.getName(1L)).thenReturn(Map.of("name", "Alice"));

        Map<String, String> result = personController.getName(1L);
        assertEquals("Alice", result.get("name"));
    }
}