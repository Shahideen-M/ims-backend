package com.example.inventory_management_system.service;

import com.example.inventory_management_system.entity.Users;
import com.example.inventory_management_system.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    private PasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

     public ResponseEntity<?> register(Users user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exist, please login");
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
            Users newUser = userRepository.save(user);
            return ResponseEntity.ok(newUser);
        }
     }

    public String verify(Users user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getEmail());
        }
        return "fail";
    }

    public Long getLoggedUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email);

        if (user == null) throw new RuntimeException("User not found");
        return user.getId();
    }

     public void deleteUser(Long id) {
         userRepository.deleteById(id);
     }

     public Users save(Users user) {
         return userRepository.save(user);
     }

    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
