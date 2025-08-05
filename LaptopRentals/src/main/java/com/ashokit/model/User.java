package com.ashokit.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer userId;
	    private String name;
	    private String phoneno;
	    private String email;
	    private String password;
	    @Column(name = "reset_token")
	    private String resetToken;
	    
	    
	    private LocalDateTime resetTokenExpiry;
	    

	    @Enumerated(EnumType.STRING)
	    private Roles role;

	  

}
