package com.example.classoa.service;

import com.example.classoa.entity.Department;
import com.example.classoa.mapper.DepartmentMapper;
import com.example.classoa.utils.MyBatisUtils;

public class DepartmentService {
    public Department selectById(Long departmentId) {
        return (Department) MyBatisUtils.executeQuery(sqlSession ->
                sqlSession.getMapper(DepartmentMapper.class).selectById(departmentId));
    }
}
