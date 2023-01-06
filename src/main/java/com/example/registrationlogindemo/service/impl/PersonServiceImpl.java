package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.dto.PersonDto;
import com.example.registrationlogindemo.entity.Person;
import com.example.registrationlogindemo.repository.PersonRepository;
import com.example.registrationlogindemo.repository.UserRepository;
import com.example.registrationlogindemo.service.PersonService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService  {

    private PersonRepository personRepository;

    private UserRepository userRepository;

    public PersonServiceImpl(PersonRepository personRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void savePerson(PersonDto personDto) {

        Person person = new Person();

        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setRut(personDto.getRut());
        person.setPhone(personDto.getPhone());
        person.setDateCreated(LocalDateTime.now());

        this.personRepository.save(person);
    }

    @Override
    public Person findByRut(String rut) {
        return this.personRepository.findByRut(rut);
    }


    @Override
    public List<PersonDto> findAllPersons() {
        List<Person> persons = personRepository.findAll();
        return persons.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public PersonDto findByUsername(String username) {
        return null;
    }


    private PersonDto convertEntityToDto(Person person){
        PersonDto personDto = new PersonDto();
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setRut(person.getRut());

        return personDto;
    }
}
