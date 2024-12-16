package gh.com.oasystem.service;

import gh.com.oasystem.model.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Long queryEmployeeIdByUserId(Long userId);
    Employee queryEmployeeById(Long employeeId);
    Employee getLeader(Long employeeId);
}
