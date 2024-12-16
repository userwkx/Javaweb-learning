package gh.com.oasystem.service.impl;

import gh.com.oasystem.mapper.NodeMapper;
import gh.com.oasystem.mapper.UserMapper;
import gh.com.oasystem.model.entity.User;
import gh.com.oasystem.service.UserService;
import gh.com.oasystem.utils.MybatisUtil;
import gh.com.oasystem.utils.Md5Utils;
import java.util.Map;

public class UserServiceImpl implements UserService {
    @Override
    public User login(String username, String password) {
        User user = (User)MybatisUtil.executeQuery(sqlSession -> {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.selectUserByUsername(username);

        });
        Integer salt=user.getSalt();
        String inputpassword= Md5Utils.md5Digest(password,salt);
        if(!inputpassword.equals(user.getPassword())){
            return null;
        }
        return user;
    }

    @Override
    public Long queryRoleIdByUserId(Long userId) {
        Map<String,Long> roleInfo = (Map<String, Long>) MybatisUtil.executeQuery(sqlSession -> {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.selectUserById(userId);
        });
        return roleInfo.get("roleId");
    }

}
