package dgu.ailab.dcloud.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "`user_role`")
@Data // Lombok의 기능으로, getter, setter, equals, hashcode, tostring 자동 생성해줌.
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Integer userRoleId;

    @Column(name = "user_userid")
    private String userUserId;

    @Column(name = "role_roleid")
    private Integer roleRoleId;

    @ManyToOne
    @JoinColumn(name = "user_userid", referencedColumnName = "userid", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_roleid", referencedColumnName = "roleid", insertable = false, updatable = false)
    private Role role;

}