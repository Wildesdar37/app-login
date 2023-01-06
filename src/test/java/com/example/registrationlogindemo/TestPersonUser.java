package com.example.registrationlogindemo;

import com.example.registrationlogindemo.entity.Person;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.PersonRepository;
import com.example.registrationlogindemo.repository.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPersonUser {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public void shouldSaveUser(){
        System.out.println("Before");
        Person person = new Person();
        person.setRut("26871346-9");
        person.setFirstName("test 1");
        person.setLastName("user");
        person.setDateCreated(LocalDateTime.now());
        person.setPhone(92123131);

        Person savedPerson = this.personRepository.save(person);

        User user = new User();
        user.setPerson(person);
        user.setUsername("test@mail.com");
        user.setPassword(passwordEncoder.encode("testpass"));

        User savedUser = userRepository.save(user);

        assertThat(savedUser).usingRecursiveComparison().ignoringFields("person_id").isEqualTo(user);
    }

    @Test
    public void shouldBeListUser(){
        System.out.println("should by list user");
        System.out.println("before clas");

        Person person = new Person();
        person.setRut("26871346-9");
        person.setFirstName("test 1");
        person.setLastName("user");
        person.setDateCreated(LocalDateTime.now());
        person.setPhone(92123131);

        Person savedPerson = personRepository.save(person);

        User user = new User();
        user.setPerson(person);
        user.setUsername("test@mail.com");
        user.setPassword(passwordEncoder.encode("testpass"));

        User savedUser = userRepository.save(user);

        User userDb = userRepository.findByUsername(savedUser.getUsername());

        assertThat(userDb.getUsername()).isEqualTo(savedUser.getUsername());
    }

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"users","roles","persons");
    }

}
