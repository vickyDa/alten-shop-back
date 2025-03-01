package com.alten.shop.payload;


import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;

    // Getters et setters
}
