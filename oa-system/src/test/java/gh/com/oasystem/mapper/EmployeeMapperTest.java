package gh.com.oasystem.mapper;

import gh.com.oasystem.utils.MybatisUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeMapperTest {

    @Test
    void queryEmployeeByemployeeId() {
        Object o = MybatisUtil.executeQuery(sqlSession -> {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
            return employeeMapper.queryEmployeeByemployeeId(1L);

        });
        System.out.println(o);
    }


    @Test
    void queryEmployeeIdByUserId() {
        Object o = MybatisUtil.executeQuery(sqlSession -> {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
            return employeeMapper.queryEmployeeByemployeeId(1L);

        });
        System.out.println(o);
    }

    @Test
    void getBoss() {
        System.out.println(MybatisUtil.executeQuery(sqlSession -> sqlSession.getMapper(EmployeeMapper.class).getBoss()));
    }

    @Test
    void getLeader() {
        System.out.println(MybatisUtil.executeQuery(sqlSession -> sqlSession.getMapper(EmployeeMapper.class).getLeader(2L)));
    }
}
