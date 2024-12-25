package com.codingshuttle.sample.w6p1_advanced_security;

import com.codingshuttle.sample.w6p1_advanced_security.entities.User;
import com.codingshuttle.sample.w6p1_advanced_security.entities.enums.Role;
import com.codingshuttle.sample.w6p1_advanced_security.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class W6P1AdvancedSecurityApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {
		Set<Role> set = new HashSet<>();
		set.add(Role.ADMIN);

		User user = new User(4L, "anuj@gmail.com", "1234", "Anuj", set);
		String token = jwtService.generateAccessToken(user);
		System.out.println(token);
		Long id = jwtService.getUserIdFromToken(token);
		System.out.println(id);
	}

}