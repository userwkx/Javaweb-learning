package gh.com.oasystem.mapper;


import gh.com.oasystem.utils.MybatisUtil;
import org.junit.jupiter.api.Test;


public class NodeMapperTest {

    @Test
    public void getAllNodes() {
        Object o = MybatisUtil.executeQuery(sqlSession -> {
            NodeMapper nodeMapper = sqlSession.getMapper(NodeMapper.class);
            return nodeMapper.getAllNodes();

        });
        System.out.println(o);
    }

    @Test
    public void selectNodesByRoleId() {
        Object o = MybatisUtil.executeQuery(sqlSession -> {
            NodeMapper nodeMapper = sqlSession.getMapper(NodeMapper.class);
            return nodeMapper.selectNodesByRoleId(2L);

        });
        System.out.println(o);
    }
}
