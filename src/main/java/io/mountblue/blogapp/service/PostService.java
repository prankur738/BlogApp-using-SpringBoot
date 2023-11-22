package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.Comment;
import io.mountblue.blogapp.entity.Post;

import java.util.List;

public interface PostService {
    void savePost(Post post, String tagString);
    void updatePost(int id);
    Post findPostById(int id);
    List<Post> getALlPosts();

}
