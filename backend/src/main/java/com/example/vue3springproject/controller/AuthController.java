package com.example.vue3springproject.controller;

import com.example.vue3springproject.dto.AuthResponse;
import com.example.vue3springproject.dto.LoginRequest;
import com.example.vue3springproject.entity.User;
import com.example.vue3springproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true"
)
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request,
            HttpSession session
            ) {
        User user = userService.authenticate(
                request.getLogin(),
                request.getPassword()
        );

        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        session.setAttribute("userId", user.getId());

        AuthResponse response = new AuthResponse(
                user.getId(),
                user.getLogin()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> me(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getUserById(userId);

        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        AuthResponse response = new AuthResponse(
                user.getId(),
                user.getLogin()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();

        return ResponseEntity.ok().build();
    }
}

