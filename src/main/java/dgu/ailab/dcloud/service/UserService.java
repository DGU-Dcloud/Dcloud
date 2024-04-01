package dgu.ailab.dcloud.service;

import dgu.ailab.dcloud.dto.SignupDto;
import dgu.ailab.dcloud.entity.Role;
import dgu.ailab.dcloud.entity.User;
import dgu.ailab.dcloud.entity.UserRole;
import dgu.ailab.dcloud.repository.UserRepository;
import dgu.ailab.dcloud.repository.RoleRepository;
import dgu.ailab.dcloud.repository.User_roleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

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
        if (role != null) {
            return role.getRoleID();
        }
        return -1;
    }
}
