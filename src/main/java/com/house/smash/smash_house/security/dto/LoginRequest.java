package com.house.smash.smash_house.security.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String nickname;
    private String password;
}