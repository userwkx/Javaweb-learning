package gh.com.oasystem.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 830991826493187997L;
    private Long userId;
    private String userName;
    private String password;
    private Long employeeId;
    private Integer salt;
}
