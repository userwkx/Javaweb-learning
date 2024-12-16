package gh.com.oasystem.mapper;

import gh.com.oasystem.service.impl.UserServiceImpl;
import gh.com.oasystem.utils.MybatisUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void selectUserByUsername() {
        Object o = MybatisUtil.executeQuery(sqlSession -> {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.selectUserByUsername("m8");

        });
        System.out.println(o);
    }
    @Test
    void selectUserById() {
        Object o = MybatisUtil.executeQuery(sqlSession -> {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.selectUserById(1L);

        });
        System.out.println(o);
    }
}