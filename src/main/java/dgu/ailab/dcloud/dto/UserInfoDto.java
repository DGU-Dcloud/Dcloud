package dgu.ailab.dcloud.dto;

import dgu.ailab.dcloud.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserInfoDto {

    private String userName;
    private String userID;
    private Date birthDate;
    private String email;

    public User toEntity() {
        User user = new User();
        user.setUserName(userName);
        user.setUserID(userID);
        user.setBirthDate(birthDate);
        user.setEmail(email);
        return user;
    }
}
