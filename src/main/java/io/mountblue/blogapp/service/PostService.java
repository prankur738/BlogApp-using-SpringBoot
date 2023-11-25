package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.Comment;
import io.mountblue.blogapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Set;

public interface PostService {
    void savePost(Post post, String tagString);
    void updatePost(Post newPost, int postId, String tagString);
    Post findPostById(int id);
    List<Post> getALlPosts();
    void deletePostById(int id);
    String getCommaSeperatedTags(int id);
//    List<Post> getPostsBySearch(String searchText);
    Page<Post> getPaginatedPosts(Pageable pr, List<String> authors, List<String> tags);
    Page<Post> getPostsBySearch(String searchText, Pageable pageable);
    Set<String> findDistinctAuthors();
    Page<Post> getPosts(List<String> authorList, List<String> tagList,
                        String search, String sortField, String order,
                        Model model, Pageable pageable);

}
