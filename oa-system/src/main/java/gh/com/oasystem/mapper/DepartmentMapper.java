package gh.com.oasystem.mapper;

import gh.com.oasystem.model.entity.Department;

import java.util.Map;

public interface DepartmentMapper {
    Department queryDepartmentById(Long departmentId);
    Long queryDepartmentIdByEmployeeId(Long employeeId);
}
