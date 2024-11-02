package com.example.softwerk.person;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "father_id")
    @JsonBackReference
    private Person father;

    @ManyToOne
    @JoinColumn(name = "mother_id")
    @JsonBackReference
    private Person mother;

    @OneToMany(mappedBy = "father")
    @JsonManagedReference 
    private List<Person> fatherToChildren = new ArrayList<>();

    @OneToMany(mappedBy = "mother")
    @JsonManagedReference 
    private List<Person> motherToChildren = new ArrayList<>();
    
    public Person() {}

    public Person(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Person getFather() {
        return father;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public Person getMother() {
        return mother;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public List<Person> getFatherToChildren() {
        return fatherToChildren;
    }

    public void setFatherToChildren(List<Person> childrenByFather) {
        this.fatherToChildren = childrenByFather;
    }

    public List<Person> getMotherToChildren() {
        return motherToChildren;
    }

    public void setMotherToChildren(List<Person> childrenByMother) {
        this.motherToChildren = childrenByMother;
    }

}
