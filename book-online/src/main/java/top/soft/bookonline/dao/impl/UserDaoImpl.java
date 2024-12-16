package top.soft.bookonline.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import top.soft.bookonline.dao.UserDao;
import top.soft.bookonline.entity.User;
import top.soft.bookonline.util.JdbcUtil;
import top.soft.bookonline.util.Md5Util;

/**
 * @author lhy
 * @description: UserDao 实现层
 * @date 2024/10/20 16:40
 */
public class UserDaoImpl implements UserDao {
    //声名Jdbd对象
    private final JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtil.getDataSource());
    @Override
    public int insertUser(User user) {
        //插入用户:sql
        String sql="insert into t_user(account,password,nickname,avatar) values(?,?,?,?)";
        return jdbcTemplate.update(sql,user.getAccount(), Md5Util.crypt(user.getPassword()),user.getNickname(),user.getAvatar());
    }

    @Override
    public User findUser(User user) {
        String sql="select * from t_user where account=? and password=?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), user.getAccount(),Md5Util.crypt(user.getPassword()));
    }

    @Override
    public User findUserById(User user) {
        String sql="select * from t_user where account=?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), user.getAccount());
    }

    @Override
    public int insertUserOther(User user) {
        String sql="insert into t_user_other(account,password,nickname,avatar) values(?,?,?,?)";
        return jdbcTemplate.update(sql,user.getAccount(), Md5Util.crypt(user.getPassword()),user.getNickname(),user.getAvatar());
    }

    @Override
    public User findUserOther(User user) {
        String sql="select * from t_user_other where account=? and password=?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), user.getAccount(),Md5Util.crypt(user.getPassword()));
    }

    @Override
    public void updateUserAddress(String userId, String address) {
        String sql = "UPDATE t_user SET address = ? WHERE id = ?";
        jdbcTemplate.update(sql, address, userId);
    }

    @Override
    public void updateUserAccount(String account, String id) {
        String sql = "UPDATE t_user SET account = ? WHERE id = ?";
        jdbcTemplate.update(sql, account, id);
    }

}
