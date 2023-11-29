package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;

public interface PostService {
    void savePost(Post post, String tagString);
    void updatePost(Post newPost, int postId, String tagString);
    Post findPostById(int id);
    void deletePostById(int id);
    String getCommaSeperatedTags(int id);
    Set<String> findDistinctAuthors();
    Page<Post> getPosts(String authors, String tags,
                        String search, String sortField, String order,
                        Pageable pageable);

    List<Post> findAll();

    boolean isUserAuthorized(UserDetails userDetails, int postId);

}
