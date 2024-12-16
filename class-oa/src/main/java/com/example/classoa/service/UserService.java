package com.example.classoa.service;

import com.example.classoa.entity.User;
import com.example.classoa.mapper.UserMapper;
import com.example.classoa.utils.Md5Utils;

import javax.security.auth.login.LoginException;

public class UserService {
    private final UserMapper userMapper =new UserMapper();
    public User login(String username, String password) throws LoginException {
        User user = userMapper.selectByUsername(username);
        if(user==null){
            throw new LoginException("用户名不存在");
        }
        String md5Password = Md5Utils.md5Digest(password, user.getSalt());
        if(md5Password.equals(user.getPassword())){
            throw new LoginException("密码错误");
        }
        return user;
    }
}
