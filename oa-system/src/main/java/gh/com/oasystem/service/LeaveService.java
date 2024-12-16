package gh.com.oasystem.service;

import gh.com.oasystem.model.entity.LeaveForm;
import gh.com.oasystem.model.vo.AuditLeaveVO;

import java.util.List;

public interface LeaveService {
     void createLeave(LeaveForm form);

     List<AuditLeaveVO> queryLeaveList(Long eid);
     void auditLeave(Long operatorId, Long formId, String result, String reason);
}
