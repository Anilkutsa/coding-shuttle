package com.codingshuttle.sample.w6p1_advanced_security.repositories;

import com.codingshuttle.sample.w6p1_advanced_security.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
