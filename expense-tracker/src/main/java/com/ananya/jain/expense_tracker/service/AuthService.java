package com.ananya.jain.expense_tracker.service;

import com.ananya.jain.expense_tracker.dto.AuthResponse;
import com.ananya.jain.expense_tracker.dto.LoginRequest;
import com.ananya.jain.expense_tracker.dto.RegisterRequest;
import com.ananya.jain.expense_tracker.entity.User;
import com.ananya.jain.expense_tracker.repository.UserRepository;
import com.ananya.jain.expense_tracker.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest registerRequest){

        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new RuntimeException("User already exists");
        }

        else {
            String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
            User user = User.builder()
                    .password(hashedPassword)
                    .email(registerRequest.getEmail())
                    .name(registerRequest.getName())
                    .build();

            userRepository.save(user);

            String jwtToken = jwtService.generateToken(registerRequest.getEmail());

            return AuthResponse.builder()
                    .jwtToken(jwtToken)
                    .build();
        }
    }

    public AuthResponse login(LoginRequest loginRequest){

            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
                throw new RuntimeException("Password is incorrect");

            }
            String jwtToken = jwtService.generateToken(loginRequest.getEmail());
            return AuthResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }
}
