package com.example.classoa.mapper;

import com.example.classoa.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void selectByUsername() {
        UserMapper userMapper = new UserMapper();
        User t7 = userMapper.selectByUsername("t7");
        System.out.println(t7.toString());

    }
}