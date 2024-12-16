package com.example.classoa.mapper;

import com.example.classoa.entity.Employee;
import com.example.classoa.utils.MyBatisUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeMapperTest {
    @Test
    void selectByParams() {
        MyBatisUtils.executeQuery(sqlSession -> {
            java.util.Map<String, Object> params = new HashMap<>();
            // 2号部门(研发部)的部门经理
            params.put("departmentId", 2);
            params.put("level", 7);
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
            List<Employee> leaders = employeeMapper.selectByParams(params);
            System.out.println(leaders.get(0));
            return leaders.get(0);
        });
    }
}