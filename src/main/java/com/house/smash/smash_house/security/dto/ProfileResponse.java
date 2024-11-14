package com.house.smash.smash_house.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileResponse {

    private String nickname;
    private String name;
    private String email;
}
