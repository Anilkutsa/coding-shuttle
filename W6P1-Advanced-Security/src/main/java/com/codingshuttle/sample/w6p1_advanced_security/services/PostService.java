package com.codingshuttle.sample.w6p1_advanced_security.services;

import com.codingshuttle.sample.w6p1_advanced_security.dto.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);
}
