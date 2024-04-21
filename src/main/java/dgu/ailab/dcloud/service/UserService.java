package dgu.ailab.dcloud.service;

import dgu.ailab.dcloud.dto.ServerDto;
import dgu.ailab.dcloud.dto.SignupDto;
import dgu.ailab.dcloud.dto.UserInfoDto;
import dgu.ailab.dcloud.entity.Role;
import dgu.ailab.dcloud.entity.User;
import dgu.ailab.dcloud.entity.UserRole;
import dgu.ailab.dcloud.repository.UserRepository;
import dgu.ailab.dcloud.repository.RoleRepository;
import dgu.ailab.dcloud.repository.User_roleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final User_roleRepository user_roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, User_roleRepository user_roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.user_roleRepository = user_roleRepository;
    }

    public boolean isUserExists(String userId) {
        return userRepository.existsByUserID(userId);
    }

    @Transactional
    public void signup(SignupDto signupDto, Integer roleId) {
        User user = signupDto.toEntity();

        userRepository.save(user);

        UserRole userRole = new UserRole();
        userRole.setUserUserId(signupDto.getUserID());
        userRole.setRoleRoleId(roleId);

        user_roleRepository.save(userRole);

        user.getUserRole().add(userRole);

        userRepository.save(user);


    }
    public int getRoleId(String roleName) {
        Role role = roleRepository.findByRoleName(roleName);
        logger.info("Retrieved roleName: {}", roleName);
        logger.info("Retrieved role: {}", role);
        if (role != null) {
            return role.getRoleID();
        }
        return -1;
    }

    public boolean authenticate(String userID, String password){
        // userRepository에서 userID로 사용자 찾음
        User user = userRepository.findByUserID(userID);

        // 해당하는 사용자가 없거나 or pw 일치하지 않으면 false
        if (user == null || !user.getPassword().equals(password)) {
            return false;
        }
        // 사용자가 존재하고, 비밀번호도 일치하면 true
        return true;
    }

//    public User findUserById(String userID) {
//        User user= userRepository.findByUserID(userID);
//        return userRepository.findByUserID(userID).orElseThrow(() -> new RuntimeException("User not found"));
//    }


    public UserInfoDto getUserInfo(String userId) {
        User user = userRepository.findByUserID(userId);
        if (user != null) {
            return new UserInfoDto(user.getUserName(), user.getUserID(), user.getBirthDate(), user.getEmail());
        } else {
            throw new RuntimeException("User not found with userId: " + userId);
        }
    }
}
