package com.codingshuttle.sample.w6p1_advanced_security.repositories;

import com.codingshuttle.sample.w6p1_advanced_security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
