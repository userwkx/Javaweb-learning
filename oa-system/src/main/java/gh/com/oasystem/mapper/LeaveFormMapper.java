package gh.com.oasystem.mapper;

import gh.com.oasystem.model.entity.LeaveForm;
import gh.com.oasystem.model.vo.AuditLeaveVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LeaveFormMapper {
    int insertLeaveForm(LeaveForm leaveForm);

    List<AuditLeaveVO> selectAuditLeaveList(@Param("eid") Long eid, @Param("state") String flowStateProcess);
    LeaveForm selectLeaveFormById(Long eid);
    void updateLeaveForm(LeaveForm leaveForm);
}
