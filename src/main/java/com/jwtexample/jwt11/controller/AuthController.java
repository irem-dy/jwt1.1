package com.jwtexample.jwt11.controller;

import com.jwtexample.jwt11.entity.User;
import com.jwtexample.jwt11.service.UserService;
import com.jwtexample.jwt11.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // ← CORS ön kontrol (OPTIONS) sorunlarını çözer
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    // Kullanıcı girişi: JWT token üret
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User requestUser) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestUser.getUsername(),
                            requestUser.getPassword()
                    )
            );

            String token = jwtUtil.generateToken(requestUser.getUsername());
            return ResponseEntity.ok().body("Bearer " + token);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Kullanıcı adı veya şifre hatalı");
        }
    }

    // Kullanıcı kaydı (şifre encode edilerek kaydedilecek)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Şifreyi encode edip kaydet
        user.setPassword(userService.getPasswordEncoder().encode(user.getPassword()));
        User newUser = userService.saveUser(user);
        return ResponseEntity.ok(newUser);
    }
}
