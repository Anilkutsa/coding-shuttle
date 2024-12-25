package com.codingshuttle.sample.w6p1_advanced_security.repositories;

import com.codingshuttle.sample.w6p1_advanced_security.entities.Session;
import com.codingshuttle.sample.w6p1_advanced_security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByUser(User user);

    Optional<Session> findByRefreshToken(String refreshToken);
}
