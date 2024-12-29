package com.codingshuttle.sample.w6p1_advanced_security.utils;

import com.codingshuttle.sample.w6p1_advanced_security.dto.PostDTO;
import com.codingshuttle.sample.w6p1_advanced_security.entities.User;
import com.codingshuttle.sample.w6p1_advanced_security.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSecurity {

    private  final PostService postService;

    public boolean isOwnerOfPost(Long postId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO post = postService.getPostById(postId);
        return post.getAuthor().getId().equals(user.getId());
    }
}
