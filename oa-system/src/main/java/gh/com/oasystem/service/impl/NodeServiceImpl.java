package gh.com.oasystem.service.impl;

import gh.com.oasystem.mapper.NodeMapper;
import gh.com.oasystem.model.entity.Node;
import gh.com.oasystem.service.NodeService;
import gh.com.oasystem.utils.MybatisUtil;

import java.util.List;

public class NodeServiceImpl implements NodeService {


    @Override
    public List<Node> queryAllNode(Long roleId) {
        return   (List<Node>)MybatisUtil.executeQuery(sqlSession -> {
            NodeMapper nodeMapper = sqlSession.getMapper(NodeMapper.class);
            return nodeMapper.selectNodesByRoleId(roleId);

        });

    }


}
