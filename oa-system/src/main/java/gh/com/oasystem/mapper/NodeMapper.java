package gh.com.oasystem.mapper;

import gh.com.oasystem.model.entity.Node;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NodeMapper {
    List<Node> getAllNodes();

    List<Node> selectNodesByRoleId(@Param("roleId")Long roleId);
}
