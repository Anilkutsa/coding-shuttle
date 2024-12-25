package com.codingshuttle.sample.w6p1_advanced_security.services;

import com.codingshuttle.sample.w6p1_advanced_security.dto.LoginDto;
import com.codingshuttle.sample.w6p1_advanced_security.dto.LoginResponseDto;
import com.codingshuttle.sample.w6p1_advanced_security.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final SessionService sessionService;

    /**
     * @param loginDto
     * @return
     * Service method to authenticate and login the user
     */
    public LoginResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        // Generate a new Session everytime user logs in into the app
        sessionService.generateNewSession(user, refreshToken);

        return new LoginResponseDto(user.getId(), accessToken, refreshToken);
    }

    /**
     * @param refreshToken
     * @return
     * Service method to generate new ACCESS TOKEN from REFRESH token
     */
    public LoginResponseDto refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        // Check session is expired, throw exception
        // If valid, update lastUsedAt time for session
        sessionService.validateSession(refreshToken);
        User user = userService.getUserById(userId);

        String accessToken = jwtService.generateAccessToken(user);
        return new LoginResponseDto(user.getId(), accessToken, refreshToken);
    }
}
