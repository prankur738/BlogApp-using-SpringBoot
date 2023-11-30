package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.Comment;

import java.util.List;

public interface CommentService {
    void saveComment(Comment comment, Integer postId);
    void updateComment(Comment comment, Integer postId);
    void deleteCommentById(Integer commentId);
    Comment getCommentById(Integer commentId);
    List<Comment> findAll();
    Comment findById(Integer commentId);

}
