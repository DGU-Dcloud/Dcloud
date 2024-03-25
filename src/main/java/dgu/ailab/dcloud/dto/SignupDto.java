package dgu.ailab.dcloud.dto;

import dgu.ailab.dcloud.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class SignupDto {
    private String userID;
    private String userName;
    private String sex;
    private Date birthDate;
    private String phone;
    private String email;
    private String password;
    private Date createdAt;
    private String refreshToken;

    public User toEntity() {
        return new User(userID, userName, sex, birthDate, phone, email, password, new Date(), refreshToken);
    }
}
