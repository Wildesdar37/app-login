package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    Person findByRut(String rut);
}