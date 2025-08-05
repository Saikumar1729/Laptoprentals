package com.ashokit.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.dtos.LoginDto;
import com.ashokit.dtos.RegisterDto;
import com.ashokit.dtos.ResetPasswordDto;
import com.ashokit.dtos.ResponseDto;
import com.ashokit.model.User;
import com.ashokit.service.JwtService;
import com.ashokit.service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto dto) {
        userService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered. Check email.");
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginDto dto) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        String token = jwtService.generateToken(dto.getEmail());
        User user = userService.findByEmail(dto.getEmail());
        return ResponseEntity.ok(new ResponseDto(token, "yes", user.getRole().toString()));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgot(@RequestBody Map<String, String> map) {
        userService.sendResetToken(map.get("email"));
        return ResponseEntity.ok("Reset token sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> reset(@RequestBody ResetPasswordDto dto) {
        userService.resetPassword(dto);
        return ResponseEntity.ok("Password reset successful");
    }
}