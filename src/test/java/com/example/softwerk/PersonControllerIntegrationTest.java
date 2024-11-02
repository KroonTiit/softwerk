//Generated with ChatGPT fixed by Tiit Kroon
package com.example.softwerk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.softwerk.person.Person;
import com.example.softwerk.person.PersonController;
import com.example.softwerk.person.PersonRepository;

@WebMvcTest(PersonController.class)
public class PersonControllerIntegrationTest  {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository repository;

    @Test
    void testGetFamilyTree() throws Exception {
        mockMvc.perform(get("/api/getFamilytree"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    //Did not get this test to pass due to time constraint.
    @Test
    void testAddPersonWithoutParents() throws Exception {
        repository.deleteAll();
        mockMvc.perform(post("/api/addPerson")
                .param("name", "TestPerson"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.name == 'TestPerson')]").exists());
        assertEquals(1, repository.count());
    }

    @Test
    void testGetFather_NotFound() throws Exception {
        given(repository.findById(anyLong())).willReturn(Optional.empty());

        mockMvc.perform(get("/api/getFather")
                .param("id", "2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetName() throws Exception {
        Person person = new Person("TestPerson");

        given(repository.findById(1L)).willReturn(Optional.of(person));

        mockMvc.perform(get("/api/getName")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestPerson"));
    }

    @Test
    void testIsSibling() throws Exception {
        Person person = new Person("Person");
        Person sibling = new Person("Sibling");

        Person father = new Person("Father");
        person.setFather(father);
        sibling.setFather(father);

        given(repository.findById(1L)).willReturn(Optional.of(person));
        given(repository.findById(2L)).willReturn(Optional.of(sibling));

        mockMvc.perform(get("/api/isSibling")
                .param("id", "1")
                .param("siblingId", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
