package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.Post;

import java.util.List;

public interface PostService {
    void savePost(Post post);
    void updatePost(int id);
    Post findById(int id);
    List<Post> getALlPosts();
}
