package com.example.springbootelasticsearch.controller;

import com.example.springbootelasticsearch.document.Person;
import com.example.springbootelasticsearch.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping("/save")
    public Person save(@RequestBody Person person){
        return personService.save(person);
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable String id){
        return personService.findById(id);
    }
}
