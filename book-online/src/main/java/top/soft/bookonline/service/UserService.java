package top.soft.bookonline.service;

import top.soft.bookonline.entity.User;

public interface UserService {
    User signIn(String account, String password);

    User register(String account, String password);

    User findRepeatUser(String account);

    User signInOther(String account , String password);
}
