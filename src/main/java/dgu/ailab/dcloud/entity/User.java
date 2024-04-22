package dgu.ailab.dcloud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@Data // Lombok의 기능으로, getter, setter, equals, hashcode, tostring 자동 생성해줌.
public class User {
    @Id // 기본키 매핑 : 사용자가 직접 사용하는 고유의 아이디. GeneratedValue 아님.
    @Column(name = "userID")
    private String userID; // id에서 userID로 변경했습니다.

    @Column(name = "userName")
    private String userName;

    @Column(name = "sex")
    private String sex;

    @Column(name = "birthDate")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "createdAt")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name = "refreshToken")
    private String refreshToken;

    //유저가 삭제되면, 유저역할도 삭제 수정 변경됨. mappedBy는 유저역할엔티티에 유저라는 필드가 있음을 뜻함.
    //cascadeType.ALL은 유저 엔티티에 저장,업데이트,삭제 명령이 유저역할 엔티티에도 적용됨을 의미한다.
    //orphanRemoval=true는 유저와 유저역할 엔티티간 참조가 제거된 경우, 해당 인스턴스는 유저역할 엔티티에서 삭제됨을 의미한다.
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<UserRole> userRole = new ArrayList<>();

    // 사용자의 역할 가져오기
    public List<Role> getRoles() {
        return userRole.stream()
                .map(UserRole::getRole)
                .collect(Collectors.toList());
    }
}