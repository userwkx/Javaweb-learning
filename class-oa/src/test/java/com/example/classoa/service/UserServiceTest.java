package com.example.classoa.service;

import com.example.classoa.entity.User;
import org.testng.annotations.Test;

import javax.security.auth.login.LoginException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private UserService userService = new UserService();

    @Test
    void login() throws LoginException {
        User login =userService.login("t7","test1");
        System.out.println(login);
    }

    @Test
    void notExistUser() throws LoginException {
        User unknowUser =userService.login("t71","test");
        System.out.println(unknowUser);
    }

    @Test
    void errorPassword() throws LoginException {
        User errorPassword =userService.login("t7","test");
        System.out.println(errorPassword);
    }
}