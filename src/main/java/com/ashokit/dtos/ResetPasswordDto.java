package com.ashokit.dtos;

import lombok.Data;

@Data
public class ResetPasswordDto {
   private String email;
    private String newPassword;

   
}
