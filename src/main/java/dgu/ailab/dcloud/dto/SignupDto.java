package dgu.ailab.dcloud.dto;

import dgu.ailab.dcloud.entity.Role;
import dgu.ailab.dcloud.entity.User;
import dgu.ailab.dcloud.entity.UserRole;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
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
    private String role="user"; // 사용자의 역할 정보


    public User toEntity() {
        User user = new User();
        user.setUserID(userID);
        user.setUserName(userName);
        user.setSex(sex);
        user.setBirthDate(birthDate);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatedAt(new Date());
        user.setRefreshToken(refreshToken);
        return user;
    }


}
