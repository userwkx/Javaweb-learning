package com.example.classoa.mapper;

import com.example.classoa.entity.User;
import com.example.classoa.utils.MyBatisUtils;

public class UserMapper {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public User selectByUsername(String username) {
        return (User) MyBatisUtils.executeQuery(
                sqlSession -> sqlSession.selectOne("com.example.classoa.mapper.UserMapper.selectByUsername", username)
        );
    }
 }
