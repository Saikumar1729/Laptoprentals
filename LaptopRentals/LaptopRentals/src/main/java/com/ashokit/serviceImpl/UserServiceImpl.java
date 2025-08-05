package com.ashokit.serviceImpl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ashokit.dtos.RegisterDto;
import com.ashokit.dtos.ResetPasswordDto;
import com.ashokit.model.User;
import com.ashokit.repository.UserRepo;
import com.ashokit.service.MailService;
import com.ashokit.service.UserInfoDetails;
import com.ashokit.service.UserService;


@Service
public class UserServiceImpl implements UserService  {

	 private final UserRepo userRepo;
	    private final PasswordEncoder passwordEncoder;
	    private final MailService mailService;

	    
	    @Autowired
	    public UserServiceImpl(UserRepo userRepo,
	                       PasswordEncoder passwordEncoder,
	                       MailService mailService) {
	        this.userRepo = userRepo;
	        this.passwordEncoder = passwordEncoder;
	        this.mailService = mailService;
	    }

	    @Override
	    public void registerUser(RegisterDto dto) {
	        if (userRepo.existsByEmail(dto.getEmail())) {
	            throw new RuntimeException("Email already exists");
	        }
	        User user = new User();
//	        user.setEmail(dto.getEmail());
//	        user.setName(dto.getName());
//	        user.setPhoneno(dto.getPhoneno());
	        BeanUtils.copyProperties(dto, user);
	        user.setPassword(passwordEncoder.encode(dto.getPassword()));
//	        user.setRole(dto.getRole());
	       
	        userRepo.save(user);

	        mailService.sendMail(
	            dto.getEmail(),
	            "Welcome to LaptopRental",
	            "Hi " + dto.getName() + ",\n\nThank you for registering with LaptopRental. Enjoy our services."
	        );
	    }

	    @Override
	    public void sendResetToken(String email) {
	        User user = userRepo.findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

	        String token = UUID.randomUUID().toString();
	        user.setResetToken(token);
	        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15));
	        userRepo.save(user);

	        mailService.sendMail(
	            email,
	            "LaptopRental - Password Reset",
	            "Use this token to reset your password: " + token + " (valid for 15 minutes)"
	        );
	    }

	    @Override
	    public void resetPassword(ResetPasswordDto dto) {
	        User user = userRepo.findByResetToken(dto.getToken())
	            .orElseThrow(() -> new RuntimeException("Invalid token"));

	        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
	            throw new RuntimeException("Token expired");
	        }

	        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
	        user.setResetToken(null);
	        user.setResetTokenExpiry(null);
	        userRepo.save(user);
	        
	        mailService.sendMail(
	                user.getEmail(),
	                "LaptopRental - Password Reset Successful",
	                "Hi " + user.getName() + ",\n\nYour password has been successfully reset. "
	                + "If you did not request this, please contact support immediately."
	            );
	    }

	    @Override
	    public User findByEmail(String email) {
	        return userRepo.findByEmail(email).orElseThrow();
	    }
	    
	    @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        User user = userRepo.findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

	        return new UserInfoDetails(user); // Fix: return a UserDetails implementation
	    }


}
