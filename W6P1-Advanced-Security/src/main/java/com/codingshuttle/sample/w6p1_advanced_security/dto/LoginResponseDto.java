package com.codingshuttle.sample.w6p1_advanced_security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginResponseDto {
    private Long id;
    private String accessToken;
    private String refreshToken;
}
