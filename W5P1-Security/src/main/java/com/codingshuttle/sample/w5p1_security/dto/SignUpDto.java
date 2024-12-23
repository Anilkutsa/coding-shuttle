package com.codingshuttle.sample.w5p1_security.dto;

import com.codingshuttle.sample.w5p1_security.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
}
