package com.ashokit.dtos;


import com.ashokit.model.Roles;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class RegisterDto {
    private String name;
    private String phoneno;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING
    )
    private Roles role;

   
}
