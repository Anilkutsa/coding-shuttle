package com.codingshuttle.sample.w5p1_security.repositories;

import com.codingshuttle.sample.w5p1_security.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
