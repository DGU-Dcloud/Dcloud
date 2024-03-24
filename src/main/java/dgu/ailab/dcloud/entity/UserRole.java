package dgu.ailab.dcloud.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "`USER_ROLE`")
@Data // Lombok의 기능으로, getter, setter, equals, hashcode, tostring 자동 생성해줌.
public class UserRole {
    @Id
    @ManyToOne // 기본적으로 ManyToOne을 통해, 피대상자? 테이블에만 코드를 작성하는게 정석이라 한다. 즉, User entity에서 @OneToMany 어노테이션을 굳이 생성하지 않아도 된다는 뜻.
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "roleID", insertable = false, updatable = false)
    private Role role;

}
