package dgu.ailab.dcloud.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    // 복합키이므로 양방향 매핑
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<UserRole> userRole = new ArrayList<>();
}