package gh.com.oasystem.service;

import gh.com.oasystem.model.entity.Node;
import gh.com.oasystem.service.impl.NodeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeServiceTest {
    private final NodeService nodeService=new NodeServiceImpl();
    @Test
    void queryAllNode() {
        List<Node> nodes=nodeService.queryAllNode(1L);
        Assertions.assertEquals(4,nodes.size());
    }
}