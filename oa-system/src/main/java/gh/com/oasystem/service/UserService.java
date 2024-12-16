package gh.com.oasystem.service;

import gh.com.oasystem.model.entity.Node;
import gh.com.oasystem.model.entity.User;

import java.util.List;


public interface UserService {
    User login(String username ,String password);
    Long queryRoleIdByUserId(Long userId);

}
