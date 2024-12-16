package com.example.classoa.service;

import com.example.classoa.entity.Department;
import junit.framework.TestCase;
import org.junit.Test;

public class DepartmentServiceTest {
    private final DepartmentService departmentService = new DepartmentService();
    @Test
    public void selectById() {
        Department department = departmentService.selectById(3L);
        System.out.println(department);
    }
}