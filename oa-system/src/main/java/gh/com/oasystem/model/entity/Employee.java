package gh.com.oasystem.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Long employeeId;
    private String name;
    private Long departmentId;
    private String title;
    private Integer level;
    private String avatar;
}
