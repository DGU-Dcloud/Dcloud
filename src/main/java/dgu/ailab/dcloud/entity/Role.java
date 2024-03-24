package dgu.ailab.dcloud.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor // 기본 생성자 생성
@Getter // 게터 메소드 생성
@Setter // 세터 메소드 생성
@Table(name = "`ROLE`")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 해당 값을 자동으로 1씩 증가시켜줌.
    @Column(name = "roleID", nullable = false)
    private Integer roleID;

    @Column(name = "roleName", length = 50)
    private String roleName;

}
