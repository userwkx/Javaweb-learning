package gh.com.oasystem.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordLeaveVO {
    private Long formId;
    private Long employeeId;
    private Integer formType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String reason;
    private LocalDateTime createTime;
    private  String state;
    private  String operatorName;
    private  String departmentName;
    private  String result;
    private  Integer isLast;
}
