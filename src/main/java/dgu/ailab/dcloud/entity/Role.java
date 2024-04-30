package dgu.ailab.dcloud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor // 기본 생성자 생성
@Getter // 게터 메소드 생성
@Setter // 세터 메소드 생성
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 해당 값을 자동으로 1씩 증가시켜줌.
    @Column(name = "roleID", nullable = false)
    private Integer roleID;

    @Column(name = "role_name", length = 50)
    private String roleName;

    // 복합키이므로 양방향 매핑
    @JsonIgnore
    @OneToMany(mappedBy = "role", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<UserRole> userRole = new ArrayList<>();

}
