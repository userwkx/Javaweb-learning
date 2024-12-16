package gh.com.oasystem.service.impl;

import gh.com.oasystem.mapper.RecordMapper;
import gh.com.oasystem.model.vo.RecordLeaveVO;
import gh.com.oasystem.service.EmployeeService;
import gh.com.oasystem.service.RecordService;
import gh.com.oasystem.utils.MybatisUtil;

import java.util.List;

public class RecordServiceImpl implements RecordService {
    private EmployeeService employeeService=new EmployeeServiceImpl();
    @Override
    public List<RecordLeaveVO> leaveRecords(Long eid) {
        return (List<RecordLeaveVO>) MybatisUtil.executeQuery(sqlSession -> {
            RecordMapper recordMapper = sqlSession.getMapper(RecordMapper.class);

            return recordMapper.queryLeaveRecord(eid);

        });

    }
}
