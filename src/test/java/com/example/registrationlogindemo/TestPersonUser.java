package com.example.registrationlogindemo;

import com.example.registrationlogindemo.entity.Person;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.PersonRepository;
import com.example.registrationlogindemo.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGetUser {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("before clas");
    }

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

    }

    @After
    public void tearDown() {

    }

}
