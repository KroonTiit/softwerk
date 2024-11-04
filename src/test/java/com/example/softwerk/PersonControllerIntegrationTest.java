//Generated with ChatGPT fixed (or not) by Tiit Kroon
package com.example.softwerk;

import com.example.softwerk.person.Person;
import com.example.softwerk.person.PersonController;
import com.example.softwerk.person.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(PersonController.class)
public class PersonControllerIntegrationTest  {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    void testHelloWorld() throws Exception {
        mockMvc.perform(get("/api/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello world"));
    }

    @Test
    void testGetAllPersons() throws Exception {
        List<Person> personList = List.of(new Person("Alice"), new Person("Bob"));
        when(personService.getAllPersons()).thenReturn(personList);

        mockMvc.perform(get("/api/getFamilytree"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Alice")))
                .andExpect(jsonPath("$[1].name", is("Bob")));
    }

    @Test
    void testAddPerson() throws Exception {
        Person child = new Person("Child");
        when(personService.addPerson("Child", null, null)).thenReturn(List.of(child));

        mockMvc.perform(post("/api/addPerson")
                .param("name", "Child"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Child")));
    }

    @Test
    void testGetFather() throws Exception {
        Person father = new Person("Father");
        when(personService.getFather(1L)).thenReturn(father);

        mockMvc.perform(get("/api/getFather").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Father")));
    }

    @Test
    void testGetMother() throws Exception {
        Person mother = new Person("Mother");
        when(personService.getMother(1L)).thenReturn(mother);

        mockMvc.perform(get("/api/getMother").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Mother")));
    }

    @Test
    void testIsSibling() throws Exception {
        when(personService.isSibling(1L, 2L)).thenReturn(true);

        mockMvc.perform(get("/api/isSibling").param("id", "1").param("siblingId", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testIsSharingParent() throws Exception {
        when(personService.isSharingParent(1L, 2L)).thenReturn(true);

        mockMvc.perform(get("/api/isSharingParent").param("id", "1").param("siblingId", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testGetName() throws Exception {
        when(personService.getName(1L)).thenReturn(Map.of("name", "Alice"));

        mockMvc.perform(get("/api/getName").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Alice")));
    }
}
