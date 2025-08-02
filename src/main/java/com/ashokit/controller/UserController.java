package com.ashokit.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.dtos.LoginDto;
import com.ashokit.dtos.RegisterDto;
import com.ashokit.dtos.ResetPasswordDto;
import com.ashokit.dtos.ResponseDto;
import com.ashokit.model.User;
import com.ashokit.repository.UserRepo;
import com.ashokit.service.JwtService;
import com.ashokit.service.UserService;


@RestController
@RequestMapping("/api/auth")
public class UserController {
	 @Autowired
	    private UserService service;

	    @Autowired
	    private AuthenticationManager manager;

	    @Autowired
	    private JwtService jwtService;

	    @Autowired
	    UserService userService;

	    @Autowired
	    private UserRepo userRepo;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    @PostMapping("/register")
	    public ResponseEntity<String> register(@RequestBody RegisterDto register) {
	        try {
	            User user = new User();
	            user.setName(register.getName());
	            user.setEmail(register.getEmail());
	            user.setPassword(register.getPassword());
	            user.setPhoneno(register.getPhoneno());
	            user.setRole(register.getRole()); // ✅ assign role

	            service.registerUser(user);
	            return new ResponseEntity<>("Registered Successfully", HttpStatus.CREATED);
	        } catch (RuntimeException e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	        }
	    }


	    @PostMapping("/login")
	    public ResponseEntity<ResponseDto> login(@RequestBody LoginDto login) {
	        ResponseDto response = new ResponseDto();
	        try {
	            Authentication authentication = manager.authenticate(
	                    new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword())
	            );

	            if (authentication.isAuthenticated()) {
	                String token = jwtService.generateToken(login.getEmail());
	                response.setToken(token);
	                response.setIsLogged("yes");
			     Optional<User> userOpt = userRepo.findByEmail(login.getEmail());
                userOpt.ifPresent(user -> response.setRole(user.getRole().name()));

	            }
	        } catch (Exception e) {
	            response.setToken(null);
	            response.setIsLogged("no");
	        }

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }


	    @PostMapping("/forgot-password")
	    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
	        String token = userService.generateResetToken(request.get("email"));
	        return ResponseEntity.ok("Reset token: " + token);
	    }



	    @PostMapping("/reset-password")
	    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto request) {
	        try {
	            String message = userService.resetPassword(request);
	            return ResponseEntity.ok(message);
	        } catch (RuntimeException e) {
	            return ResponseEntity.badRequest().body(e.getMessage());
	        }
	    }

}
