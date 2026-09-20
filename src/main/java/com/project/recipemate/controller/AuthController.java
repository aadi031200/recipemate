package com.project.recipemate.controller;

import com.project.recipemate.dtos.auth.AuthResponseDTO;
import com.project.recipemate.dtos.auth.LoginRequestDTO;
import com.project.recipemate.dtos.auth.RegisterRequestDTO;
import com.project.recipemate.entities.User;
import com.project.recipemate.repository.UserRepository;
import com.project.recipemate.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new UsernameNotFoundException("Email already registered");
        }
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user=userRepository.save(user);

        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new AuthResponseDTO(token, user.getUserId(), user.getName(),  user.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user=userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new AuthResponseDTO(token, user.getUserId(), user.getName(),  user.getEmail()));
    }

}
