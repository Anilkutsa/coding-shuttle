package com.codingshuttle.w4p1.prod_ready_features.services;

import com.codingshuttle.w4p1.prod_ready_features.dto.PostDTO;

import java.util.List;

public interface PostService {

    PostDTO createNewPost(PostDTO inputPost);

    List<PostDTO> getAllPosts();

    PostDTO getPostById(Long postId);

    PostDTO updatePost(PostDTO inputPost, Long postId);
}
