package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.Comment;
import io.mountblue.blogapp.entity.Post;
import io.mountblue.blogapp.repository.CommentRepository;
import io.mountblue.blogapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    @Autowired
    CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository){
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }
    @Override
    public void saveComment(Comment comment, int postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if(postOptional.isPresent()){
            Post post = postOptional.get();
            comment.setPost(post);
            commentRepository.save(comment);
        }
    }
}
