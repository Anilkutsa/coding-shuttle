package com.codingshuttle.w4p1.prod_ready_features.services;

import com.codingshuttle.w4p1.prod_ready_features.dto.PostDTO;

import java.util.List;

public interface PostService {

    public PostDTO createNewPost(PostDTO inputPost);

    public List<PostDTO> getAllPosts();

    public PostDTO getPostById(Long postId);
}
