package com.codingshuttle.sample.w5p1_security.services;

import com.codingshuttle.sample.w5p1_security.dto.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);
}
