package pharmacy_webapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pharmacy_webapp.dto.ApiResponse;
import pharmacy_webapp.dto.AuthRequest;
import pharmacy_webapp.dto.AuthResponse;
import pharmacy_webapp.model.User;
import pharmacy_webapp.service.AuthService;
import pharmacy_webapp.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> registerUser(@RequestBody AuthRequest authRequest) {
        try{
            User user = authService.registerUser(authRequest);

            AuthResponse authResponse = new AuthResponse(
                    user.getId(),
                    user.getUsername(),
                    null
            );

            return ResponseEntity.ok(
                    ApiResponse.success("User registered successfully!", authResponse)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> loginUser(@RequestBody AuthRequest authRequest) {
        try{
            String token = authService.verifyUser(authRequest);

            User user = userService.getUserByUsername(authRequest.getUsername());

            AuthResponse authResponse = new AuthResponse(
                    user.getId(),
                    user.getUsername(),
                    token
            );

            return ResponseEntity.ok(
                    ApiResponse.success("User logged in successfully!", authResponse)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logoutUser(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");

            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body(
                        ApiResponse.error("No token provided")
                );
            }

            String token = authHeader.substring(7);
            authService.logoutUser(token);

            return ResponseEntity.ok(
                    ApiResponse.success("User logged out successfully!", null)
            );
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }
}
