package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.PersonDto;
import com.example.registrationlogindemo.entity.Person;

import java.util.List;


public interface PersonService {

    void savePerson(PersonDto personDto);

    Person findByRut(String rut);

    List<PersonDto> findAllPersons();

    PersonDto findByUsername(String username);

}
