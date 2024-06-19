package dgu.ailab.dcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDto {
    private String userId;
    private Integer roleId; // role_roleid를 저장하는 필드

    // 생성자 및 기타 메소드 생략
}
