package io.mountblue.blogapp.restcontroller;

import io.mountblue.blogapp.entity.Comment;
import io.mountblue.blogapp.entity.Post;
import io.mountblue.blogapp.service.CommentService;
import io.mountblue.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class CommentRestController {

    private final CommentService commentService;
    private final PostService postService;

    @Autowired
    CommentRestController(CommentService commentService, PostService postService){
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("/comments/{postId}")
    public ResponseEntity<String> saveNewComment(@PathVariable("postId") int postId,
                                                 @RequestBody Comment comment){

        if( postService.findPostById(postId) == null){
            return new ResponseEntity<>("Invalid Post id",HttpStatus.BAD_REQUEST);
        }

        commentService.saveComment(comment,postId);

        return new ResponseEntity<>("Comment saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> showAllComments(){
        return new ResponseEntity<>(commentService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/comments/{postId}")
    public ResponseEntity<List<Comment>> showAllCommentsByPostId(@PathVariable("postId") int postId){
        Post post = postService.findPostById(postId);

        if(post == null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

        return  new ResponseEntity<>(post.getComments(),HttpStatus.OK);
    }

    @PutMapping("/comments/{postId}/{commentId}")
    public ResponseEntity<String> updateCommentById(@AuthenticationPrincipal UserDetails userDetails,
                                                    @PathVariable("postId") int postId,
                                                    @PathVariable("commentId") int commentId,
                                                    @RequestBody Comment comment){

        Post post = postService.findPostById(postId);
        Comment oldComment = commentService.getCommentById(commentId);
        boolean isAuthorized = postService.isUserAuthorized(userDetails, postId);

        if(post==null || oldComment == null || !isAuthorized){
            return new ResponseEntity<>("Invalid",HttpStatus.BAD_REQUEST);
        }

        comment.setId(commentId);
        commentService.updateComment(comment,postId);

        return new ResponseEntity<>("Comment updated successfully",HttpStatus.OK);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable("commentId") int commentId){
        Comment comment = commentService.findById(commentId);

        if(comment == null){
            return new ResponseEntity<>("Invalid Comment Id",HttpStatus.BAD_REQUEST);
        }

        commentService.deleteCommentById(commentId);

        return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);
    }






}
