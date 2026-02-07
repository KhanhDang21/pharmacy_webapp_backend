package pharmacy_webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pharmacy_webapp.dto.ApiResponse;
import pharmacy_webapp.dto.UserProfileDto;
import pharmacy_webapp.model.User;
import pharmacy_webapp.model.UserPrincipal;
import pharmacy_webapp.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    //@PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileDto>> getUserProfile(Authentication authentication) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            String userId = userPrincipal.getUserId();
            User user = userService.getUserById(userId);

            UserProfileDto userProfileDto = new UserProfileDto(
                    user.getFullName(),
                    user.getEmail(),
                    user.getBirthDate(),
                    user.getPhoneNumber(),
                    user.getGender()
            );

            return ResponseEntity.ok(
                    ApiResponse.success("Get user profile successfully", userProfileDto)
            );
        } catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    //@PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.userId")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfileDto>> getUserById(@PathVariable String userId) {
        try{
            User user = userService.getUserById(userId);

            UserProfileDto userProfileDto = new UserProfileDto(
                    user.getFullName(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getBirthDate(),
                    user.getGender()
            );

            return ResponseEntity.ok(
                    ApiResponse.success("Get user successfully", userProfileDto)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @PutMapping("/update-profile")
    public ResponseEntity<ApiResponse<UserProfileDto>> updateProfile(Authentication authentication, @RequestBody UserProfileDto userProfileDto){
        try{
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            String userId = userPrincipal.getUserId();
            User user = userService.updateProfile(userId, userProfileDto);

            UserProfileDto userProfileDtoUpdated = new UserProfileDto(
                    user.getFullName(),
                    user.getEmail(),
                    user.getBirthDate(),
                    user.getPhoneNumber(),
                    user.getGender()
            );

            return ResponseEntity.ok(
                    ApiResponse.success("Update profile successfully", userProfileDtoUpdated)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    // for admin
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        try{
            List<User> users = userService.getAllUsers();

            return ResponseEntity.ok(
                    ApiResponse.success("Get all users successfully", users)
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }
}
