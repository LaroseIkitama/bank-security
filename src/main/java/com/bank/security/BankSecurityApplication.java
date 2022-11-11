package com.bank.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bank.security.domain.Role;
import com.bank.security.domain.User;
import com.bank.security.entity.Gender;
import com.bank.security.service.RoleService;
import com.bank.security.service.UserService;

import java.util.ArrayList;

@SpringBootApplication
public class BankSecurityApplication {

    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(BankSecurityApplication.class, args);
    }

    @PostConstruct
//    void initData() {
//        //add roles
//        userService.saveRole(new Role(1L, "ROLE_ADMIN"));
//        userService.saveRole(new Role(2L, "ROLE_USER"));
//        userService.saveRole(new Role(3L, "ROLE_MANAGER"));
//        userService.saveRole(new Role(4L, "ROLE_SUPER_ADMIN"));
//        //add users
//        userService.saveUser(new User(1L, "EBONGA", "Larose","rosa", Gender.FEMALE, "SENEGALE", "Dakar", "Fass rue Fa 17", "ebongalarose@gmail.com", "123", null));
//        userService.saveUser(new User(2L, "NGOR", "Seck","ngor seck", Gender.MALE, "SENEGALE", "Dakar", "Keur massar 17", "ngorseck@gmail.com", "123", null));
//        userService.saveUser(new User(3L, "ABDOULAYE", "Ngueye","Mr toto", Gender.MALE, "SENEGALE", "Dakar", "Medina rue Fa 17", "abdoulayengueye@gmail.com", "123", null));
//        userService.saveUser(new User(4L, "ABDOUL", "Azize","Djambar", Gender.MALE, "SENEGALE", "Dakar", "Medina rue Fa 22", "abdoulayengueye@gmail.com", "123", null));
//        //add roles to user
//
//        userService.addRoleToUser("rosa", "ROLE_SUPER_ADMIN");
//        userService.addRoleToUser("ngor seck", "ROLE_ADMIN");
//        userService.addRoleToUser("Mr toto", "USER");
//        userService.addRoleToUser("Djambar", "ROLE_USER");
//        userService.addRoleToUser("rosa", "ROLE_ADMIN");
//    }

    @Bean
    BCryptPasswordEncoder getBCE() {
        return new BCryptPasswordEncoder();
    }
}
