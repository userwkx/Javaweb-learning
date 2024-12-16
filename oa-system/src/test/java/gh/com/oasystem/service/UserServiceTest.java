package gh.com.oasystem.service;

import gh.com.oasystem.mapper.NodeMapper;
import gh.com.oasystem.model.entity.User;
import gh.com.oasystem.service.impl.UserServiceImpl;
import gh.com.oasystem.utils.MybatisUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService=new UserServiceImpl();
    @Test
    void login() {
        User loginUser=userService.login("m8","1234");
        assertNotNull(loginUser);
    }

    @Test
    void queryRoleIdByUserId() {
        Long roleId=userService.queryRoleIdByUserId(1L);
        System.out.println(roleId);
    }
}