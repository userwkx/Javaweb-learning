package gh.com.oasystem.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LevelEnum {
    BOSS(8,"总经理"),
    DEPARTMENT_MANAGER(7,"部门经理"),;
    final int level;
    final String desc;
}
