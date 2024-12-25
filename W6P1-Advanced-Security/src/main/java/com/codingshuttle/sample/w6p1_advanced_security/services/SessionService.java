package com.codingshuttle.sample.w6p1_advanced_security.services;

import com.codingshuttle.sample.w6p1_advanced_security.entities.Session;
import com.codingshuttle.sample.w6p1_advanced_security.entities.User;
import com.codingshuttle.sample.w6p1_advanced_security.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT = 2;

    /**
     * @param user
     * @param refreshToken
     * Generates new and saves new Session event
     * If number of sessions exceed LIMIT, delete the last used session
     */
    public void generateNewSession(User user, String refreshToken) {
        List<Session> userSessions = sessionRepository.findByUser(user);
        if (userSessions.size() == SESSION_LIMIT) {
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));

            Session leastRecentlyUsedSession = userSessions.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);
        }

        Session newSession = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
        sessionRepository.save(newSession);
    }

    /**
     * @param refreshToken
     * Checks if session data exists in DB  for refreshToken
     */
    public void validateSession(String refreshToken) {
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException("Session not found for refreshToken: " + refreshToken));
        // Set LastUsedAt time everytime we are validating the session
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }

}
