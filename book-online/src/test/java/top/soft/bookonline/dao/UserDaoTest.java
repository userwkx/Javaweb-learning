package top.soft.bookonline.dao;

import org.junit.jupiter.api.Test;
import top.soft.bookonline.dao.impl.UserDaoImpl;
import top.soft.bookonline.entity.User;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    void insertUser() {
        UserDao userDao=new UserDaoImpl();
        User user= User.builder()
                .account("wkx")
                .nickname("wkx").password("123456").address("南京").avatar("null").build();
        userDao.insertUser(user);
    }


}