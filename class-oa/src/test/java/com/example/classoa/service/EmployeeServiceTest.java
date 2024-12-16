package com.example.classoa.service;

import com.example.classoa.entity.Employee;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {
    private final EmployeeService employeeService = new EmployeeService();
    @Test
    public void selectById(){
        Employee employee = employeeService.selectById(1L);
        System.out.println(employee);
    }

    @Test
    public void selectLeader(){
        Employee leader = employeeService.selectLeader(4L);
        System.out.println(leader);
    }


}