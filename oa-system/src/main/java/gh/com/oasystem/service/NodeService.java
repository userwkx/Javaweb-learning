package gh.com.oasystem.service;

import gh.com.oasystem.model.entity.Node;

import java.util.List;


public interface NodeService {
    List<Node> queryAllNode(Long roleId);
}
