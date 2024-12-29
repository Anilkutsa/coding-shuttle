package com.codingshuttle.sample.w6p1_advanced_security.dto;

import com.codingshuttle.sample.w6p1_advanced_security.entities.enums.Permission;
import com.codingshuttle.sample.w6p1_advanced_security.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
    private Set<Permission> permissions;
}
