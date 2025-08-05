package com.ashokit.service;
//import java.util.Optional;
//import java.util.UUID;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.ashokit.dtos.ResetPasswordDto;
//import com.ashokit.model.User;
//import com.ashokit.repository.UserRepo;
//
//@Service
//public class UserService implements UserDetailsService {
//
//    private final UserRepo userRepository;
//    public UserService(UserRepo userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    // For Spring Security
////    @Override
////    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
////        return userRepository.findByEmail(email)
////                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
////    }
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//        return new UserInfoDetails(user);
//    }
//
//
//    // For your custom registration logic
//    public User registerUser(User user) {
//        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword())); // Encrypt password
//        return userRepository.save(user);
//    }
//
//
//    public String generateResetToken(String email) {
//        Optional<User> userOpt = userRepository.findByEmail(email);
//        if (userOpt.isEmpty()) {
//            throw new RuntimeException("User not found");
//        }
//
//        User user = userOpt.get();
//        String token = UUID.randomUUID().toString();
//        user.setResetToken(token);
//        userRepository.save(user);
//
//        return token;
//    }
//
//    public void resetPassword(String token, String newPassword) {
//        Optional<User> userOpt = userRepository.findByResetToken(token);
//        if (userOpt.isEmpty()) {
//            throw new RuntimeException("Invalid token");
//        }
//
//        User user = userOpt.get();
//        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
//        user.setResetToken(null);
//        userRepository.save(user);
//    }
//
//    public String resetPassword(ResetPasswordDto request) {
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Hash and set the new password
//        user.setPassword(new BCryptPasswordEncoder().encode(request.getNewPassword()));
//        userRepository.save(user);
//
//        return "Password reset successfully";
//    }
//
//}

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ashokit.dtos.RegisterDto;
import com.ashokit.dtos.ResetPasswordDto;
import com.ashokit.model.User;

public interface UserService extends  UserDetailsService {
    void registerUser(RegisterDto dto);
    void sendResetToken(String email);
    void resetPassword(ResetPasswordDto dto);
    User findByEmail(String email);
  
}
