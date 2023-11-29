package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.Comment;

import java.util.List;

public interface CommentService {
    void saveComment(Comment comment, int postId);
    void updateComment(Comment comment, int postId);
    void deleteCommentById(int commentId);
    Comment getCommentById(int commentId);
    List<Comment> findAll();
    Comment findById(int commentId);

}
