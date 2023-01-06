package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.PersonDto;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Person;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.service.PersonService;
import com.example.registrationlogindemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private UserService userService;

    private PersonService personService;

    public AuthController(UserService userService, PersonService personService) {
        this.userService = userService;
        this.personService = personService;
    }

    @GetMapping("index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model){
        PersonDto person = new PersonDto();

        model.addAttribute("person", person);
        return "register";
    }

    @GetMapping("/register/userForm")
    public String  showUserForm( Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);

        return "userRegister";
    }

    @PostMapping("/register/saveUser")
    public String userFormSave(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model,@RequestParam(name = "rut")
                               String rut
                               )  {
        User existing = userService.findByUsername(userDto.getUsername());

        if(existing != null ) {
            result.rejectValue("username",null, "There is already an account registered with that username");
        }

        if(result.hasErrors()) {
            model.addAttribute("user",userDto);
            model.addAttribute("rut", rut);
            return "userRegister";
        }

        Person user_db = personService.findByRut(rut);

        if(user_db == null) {
            model.addAttribute("error", true);
            model.addAttribute("rut", rut);
            return "userRegister";
        }

        userService.saveUser(userDto, user_db);
        return "redirect:/register?success";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("person") PersonDto personDto,
                               BindingResult result,
                               Model model){
        Person existing = personService.findByRut(personDto.getRut());

        if (existing != null) {
            User user = existing.getUser();

            if(user == null) {
                model.addAttribute("rut",existing.getRut());
                return "redirect:/register/userForm?success&rut="+existing.getRut();
            }

            result.rejectValue("rut", null, "There is already an account registered with that rut");
        }

        if (result.hasErrors()) {
            model.addAttribute("person", personDto);
            return "register";
        }

        personService.savePerson(personDto);
        //return "redirect:/register?success";
        return "redirect:/register/userForm?success&rut="+personDto.getRut();
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model, Authentication authentication){
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Person persons = personService.findByRut(user.getPerson().getRut());
        model.addAttribute("persons", persons);
        return "users";
    }


}
