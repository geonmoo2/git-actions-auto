package com.example.sl.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OAuthUserDto {
    private String username;
    private String password;
    private String role;

    //OAUTH2
    private String provider;
    private String providerId;
}
