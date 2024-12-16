package gh.com.oasystem.mapper;


import gh.com.oasystem.model.entity.Employee;
import org.apache.ibatis.annotations.Param;


public interface EmployeeMapper {

    Employee queryEmployeeByemployeeId(Long employeeId);
    Long queryEmployeeIdByUserId(Long userId);
    Employee getBoss();
    Employee getLeader(@Param("departmentId")Long departmentId);
}
