package gh.com.oasystem.mapper;

import gh.com.oasystem.model.vo.RecordLeaveVO;

import java.util.List;

public interface RecordMapper {
    List<RecordLeaveVO> queryLeaveRecord(Long eid);
}
