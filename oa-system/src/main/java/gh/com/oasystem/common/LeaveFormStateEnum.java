package gh.com.oasystem.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LeaveFormStateEnum {

    APPROVED("approved","同意"),
    REFUSED("refused","拒绝"),
    PROCESSING("processing","流程中"),;
    private final String value;
    private final String desc;

}
