package com.house.smash.smash_house.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String nickname;
    private String name;
    private String email;

    public JwtResponse(String token, String nickname, String name, String email) {
        this.token = token;
        this.nickname = nickname;
        this.name = name;
        this.email = email;
    }
}
