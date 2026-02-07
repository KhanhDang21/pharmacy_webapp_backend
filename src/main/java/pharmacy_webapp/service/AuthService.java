package pharmacy_webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pharmacy_webapp.dto.AuthRequest;
import pharmacy_webapp.model.User;
import pharmacy_webapp.repository.UserRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BlackListedTokenService blackListedTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User registerUser(AuthRequest authRequest) {
        if(authRequest.getUsername() == null || authRequest.getPassword() == null) {
            return null;
        }

        if(userRepository.existsByUsername(authRequest.getUsername())) {
            throw new RuntimeException("Username is already in use");
        }

        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(encoder.encode(authRequest.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public String verifyUser(AuthRequest authRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );

            if(authentication.isAuthenticated()) {
                return jwtService.generateToken(authRequest.getUsername());
            }
        } catch (Exception e){
            throw new RuntimeException("Invalid username or password");
        }
        throw new RuntimeException("Invalid username or password");
    }

    public void logoutUser(String token) {
        if(blackListedTokenService.isBlackListed(token)) {
            throw new RuntimeException("Blacklisted");
        }

        Date expiryTime = jwtService.extractExpiration(token);

        blackListedTokenService.blackList(token, expiryTime);
    }
}
