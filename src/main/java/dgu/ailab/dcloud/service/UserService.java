package dgu.ailab.dcloud.service;

import dgu.ailab.dcloud.dto.SignupDto;
import dgu.ailab.dcloud.entity.User;
import dgu.ailab.dcloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUserExists(String userId) {
        return userRepository.existsByUserID(userId);
    }

    public void signup(SignupDto signupDto) {
        User user = signupDto.toEntity();
        userRepository.save(user);
    }
}
