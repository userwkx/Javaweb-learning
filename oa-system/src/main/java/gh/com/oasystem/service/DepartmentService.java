package gh.com.oasystem.service;

import gh.com.oasystem.model.entity.Department;

public interface DepartmentService {
    Department queryDepartmentById(Long departmentId);
    Long queryDepartmentIdByEmployeeId(Long employeeId);
}
