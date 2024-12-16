package gh.com.oasystem.service;

import gh.com.oasystem.model.vo.RecordLeaveVO;

import java.util.List;

public interface RecordService {
    List<RecordLeaveVO> leaveRecords(Long eid);
}
