package gh.com.oasystem.service.impl;

import gh.com.oasystem.mapper.EmployeeMapper;
import gh.com.oasystem.model.entity.Employee;
import gh.com.oasystem.service.EmployeeService;
import gh.com.oasystem.utils.MybatisUtil;
import gh.com.oasystem.common.LevelEnum;

public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public Long queryEmployeeIdByUserId(Long userId) {
        Long employee = (Long) MybatisUtil.executeQuery(sqlSession -> {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
            return employeeMapper.queryEmployeeIdByUserId(userId);
        });
        return employee;
    }



    @Override
    public Employee queryEmployeeById(Long employeeId) {
        Employee employee = (Employee) MybatisUtil.executeQuery(sqlSession -> {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
            return employeeMapper.queryEmployeeByemployeeId(employeeId);

        });
        if(employee==null){
            throw new RuntimeException("用户不存在");
        }
        return employee;
    }

    @Override
    public Employee getLeader(Long employeeId) {
        return (Employee) MybatisUtil.executeQuery(sqlSession -> {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = employeeMapper.queryEmployeeByemployeeId(employeeId);
            System.out.println(employee);
            System.out.println(LevelEnum.DEPARTMENT_MANAGER.getLevel());

            Employee leader;
            if(employee.getLevel().equals(LevelEnum.DEPARTMENT_MANAGER.getLevel())){
                leader = employeeMapper.getBoss();
            }else {

                leader = employeeMapper.getLeader(employee.getDepartmentId());
            }
            return leader;
        });
    }
}
