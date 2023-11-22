package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.Comment;

public interface CommentService {
    void saveComment(Comment comment, int postId);

}
