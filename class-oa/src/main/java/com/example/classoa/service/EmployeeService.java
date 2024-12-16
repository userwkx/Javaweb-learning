package com.example.classoa.service;

import com.example.classoa.entity.Employee;
import com.example.classoa.mapper.EmployeeMapper;
import com.example.classoa.utils.MyBatisUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

public class EmployeeService {
    public Employee selectById(Long employeeId) {
        return (Employee) MyBatisUtils.executeQuery(sqlSession -> {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            return mapper.selectById(employeeId);
        });
    }
    public Employee selectLeader(Long employeeId) {
        return (Employee) MyBatisUtils.executeQuery(sqlSession -> {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.selectById(employeeId);
            java.util.Map<String, Object> params =new HashMap<>();
            Employee leader = null;
            if(employee.getLevel()<7){
                params.put("level",7);
                params.put("departmentId",employee.getDepartmentId());
                List<Employee> employees = mapper.selectByParams(params);
                leader = employees.get(0);
            } else if(employee.getLevel()==7){
                params.put("level",8);
                List<Employee> employees = mapper.selectByParams(params);
                leader = employees.get(0);
            }else if(employee.getLevel()==8){
                leader =employee;
            }
            return leader;
        });
    }
}
