package com.ashokit.dtos;


import lombok.Data;

@Data
public class ResponseDto {
    private String token;
    private String isLogged;
    private String role;

  
}
