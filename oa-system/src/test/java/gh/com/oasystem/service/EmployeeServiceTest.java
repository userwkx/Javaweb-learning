package gh.com.oasystem.service;

import gh.com.oasystem.model.entity.Employee;
import gh.com.oasystem.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
    private EmployeeService employeeService =new EmployeeServiceImpl();
    @Test
    void queryEmployeeIdByUserId() {
        Long employeeId=employeeService.queryEmployeeIdByUserId(1L);
        System.out.println(employeeId);
    }

    @Test
    void queryEmployeeById() {
        Employee employee =employeeService.queryEmployeeById(1L);
        System.out.println(employee);
    }
}
