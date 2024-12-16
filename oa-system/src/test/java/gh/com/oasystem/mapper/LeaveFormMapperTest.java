package gh.com.oasystem.mapper;

import gh.com.oasystem.model.entity.LeaveForm;
import gh.com.oasystem.utils.MybatisUtil;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import gh.com.oasystem.common.LeaveFormStateEnum;


class LeaveFormMapperTest {

    @Test
    void insertLeaveForm() {
        LeaveForm leaveForm = LeaveForm.builder()
                .employeeId(1L)
                .formType(1)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .reason("mapper测试")
                .createTime(LocalDateTime.now())
                .state(LeaveFormStateEnum.PROCESSING.getValue())
                .build();
        MybatisUtil.executeUpdate(sqlSession -> {
            LeaveFormMapper mapper = sqlSession.getMapper(LeaveFormMapper.class);
            int result = mapper.insertLeaveForm(leaveForm);
            System.out.println(result);
            return result;
        });
    }
}