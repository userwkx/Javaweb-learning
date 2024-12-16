package top.soft.bookonline.dao;

import top.soft.bookonline.entity.User;

public interface UserDao {
    /**
     * 新增用户
     * @param user
     * @return
     */
    int insertUser(User user);

    /***
     * 根据账号密码查用户
     * @param user
     * @return
     */
    User findUser(User user);

    /*
      根据账号查用户
     */
    User findUserById(User user);

    int insertUserOther(User user);

    User findUserOther(User user);

    void updateUserAddress(String address , String id);

    void updateUserAccount(String account, String id);
}
