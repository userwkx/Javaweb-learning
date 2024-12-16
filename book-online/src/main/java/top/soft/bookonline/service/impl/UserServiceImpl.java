package top.soft.bookonline.service.impl;

import top.soft.bookonline.dao.UserDao;
import top.soft.bookonline.dao.impl.UserDaoImpl;
import top.soft.bookonline.entity.User;
import top.soft.bookonline.service.UserService;

/**
 * @author Kieran
 * @description: TODO javaweb-learning
 * @date 2024/10/26 下午2:11
 */
public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();

    /**
     * 用户登录
     * @param account 用户账号
     * @param password 用户密码
     * @return 用户对象
     */
    @Override
    public User signIn(String account, String password) {
        User user = User.builder().account(account).password(password).build();
        return userDao.findUser(user);
    }

    @Override
    public User register(String account, String password) {
        User user = User.builder().account(account).password(password).build();
        userDao.insertUser(user);
        return user;
    }

    @Override
    public User findRepeatUser(String account) {
        User user = User.builder().account(account).build();
        return userDao.findUserById(user);
    }

    @Override
    public User signInOther(String account , String password) {
        User user = User.builder().account(account).password(password).build();
        return userDao.findUserOther(user);
    }

}
