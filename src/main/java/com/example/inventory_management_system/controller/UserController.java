package com.example.inventory_management_system.controller;

import com.example.inventory_management_system.entity.Users;
import com.example.inventory_management_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user) {
        userService.register(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        String token = userService.verify(user);
        Users dbuser = userService.findByEmail(user.getEmail());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "id", dbuser.getId(),
                "username", dbuser.getUsername(),
                "email", dbuser.getEmail()
        ));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMyAccount() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userService.findByEmail(email);

        if (user == null) return ResponseEntity.status(404).body("User not found");

        userService.deleteUser(user.getId());
        return ResponseEntity.ok("Account deleted successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<?> loggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody Users user) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users existingUser = userService.findByEmail(email);
        if (existingUser == null) return ResponseEntity.status(404).body("User not found");
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            existingUser.setEmail(user.getEmail());
        }

        Users updatedUser = userService.save(existingUser);
        return ResponseEntity.ok(updatedUser);
    }
}
