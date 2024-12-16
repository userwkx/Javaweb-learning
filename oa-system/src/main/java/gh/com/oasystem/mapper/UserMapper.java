package gh.com.oasystem.mapper;

import gh.com.oasystem.model.entity.User;

import java.util.Map;

public interface UserMapper {
    User selectUserByUsername(String username);

    Map<String,Long> selectUserById(Long id);
}
