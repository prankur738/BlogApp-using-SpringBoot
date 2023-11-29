package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.Comment;
import io.mountblue.blogapp.entity.Post;
import io.mountblue.blogapp.repository.CommentRepository;
import io.mountblue.blogapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    @Override
    public void deleteCommentById(int commentId) {
        commentRepository.deleteById(commentId);
    }
    @Override
    public Comment getCommentById(int commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        return commentOptional.orElse(null);
    }
    @Override
    public void updateComment(Comment comment, int postId) {
        Comment commentById = getCommentById(comment.getId());

        comment.setName(commentById.getName());
        comment.setEmail(commentById.getEmail());
        comment.setCreatedAt(commentById.getCreatedAt());

        saveComment(comment,postId);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(int commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        return commentOptional.orElse(null);
    }

}
