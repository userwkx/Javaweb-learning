package gh.com.oasystem.service.impl;

import gh.com.oasystem.mapper.DepartmentMapper;
import gh.com.oasystem.mapper.EmployeeMapper;
import gh.com.oasystem.mapper.NodeMapper;
import gh.com.oasystem.model.entity.Department;
import gh.com.oasystem.model.entity.Employee;
import gh.com.oasystem.model.entity.Node;
import gh.com.oasystem.service.DepartmentService;
import gh.com.oasystem.utils.MybatisUtil;

import java.util.List;
import java.util.Map;

public class DepartmentServiceImpl implements DepartmentService {
    @Override
    public Department queryDepartmentById(Long departmentId) {
        Department department = (Department) MybatisUtil.executeQuery(sqlSession -> {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            return departmentMapper.queryDepartmentById(departmentId);

        });
        return department;
    }

    @Override
    public Long queryDepartmentIdByEmployeeId(Long employeeId) {
        Long departmentId = ( Long) MybatisUtil.executeQuery(sqlSession -> {
            DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
            return departmentMapper.queryDepartmentIdByEmployeeId(employeeId);
        });
        return departmentId;
    }
}
