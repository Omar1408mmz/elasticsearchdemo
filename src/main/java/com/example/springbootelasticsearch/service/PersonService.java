package com.example.springbootelasticsearch.service;

import com.example.springbootelasticsearch.document.Person;
import com.example.springbootelasticsearch.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;


    public Person save(final Person person){
        return personRepository.save(person);
    }

    public Person findById(String id){
        return personRepository.findById(id).orElse(null);
    }

}
