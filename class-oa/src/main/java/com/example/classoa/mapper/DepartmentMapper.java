package com.example.classoa.mapper;

import com.example.classoa.entity.Department;
import com.example.classoa.entity.Node;

import java.util.List;

public interface DepartmentMapper {
    Department selectById(Long departmentId);
}
