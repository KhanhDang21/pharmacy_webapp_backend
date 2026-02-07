package pharmacy_webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharmacy_webapp.dto.UserProfileDto;
import pharmacy_webapp.model.User;
import pharmacy_webapp.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateProfile(String userId, UserProfileDto userProfileRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(userProfileRequest.getFullName() != null) {
            user.setFullName(userProfileRequest.getFullName());
        }
        if(userProfileRequest.getEmail() != null) {
            user.setEmail(userProfileRequest.getEmail());
        }
        if(userProfileRequest.getPhoneNumber() != null) {
            user.setPhoneNumber(userProfileRequest.getPhoneNumber());
        }
        if(userProfileRequest.getBirthDate() != null) {
            user.setBirthDate(userProfileRequest.getBirthDate());
        }
        if(userProfileRequest.getGender() != null) {
            user.setGender(userProfileRequest.getGender());
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User addRoleToUser(String userId, String roleName){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getRoles().add(roleName);
        return userRepository.save(user);
    }

    public User removeRoleFromUser(String userId, String roleName){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getRoles().remove(roleName);
        return userRepository.save(user);
    }
}
