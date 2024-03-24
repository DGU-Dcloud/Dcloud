package dgu.ailab.dcloud.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "`USER`")
@Data // Lombok의 기능으로, getter, setter, equals, hashcode, tostring 자동 생성해줌.
public class User {
    @Id
    @Column(name = "userID")
    private String id;

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
}