package io.mountblue.blogapp.controller;

import io.mountblue.blogapp.entity.Comment;
import io.mountblue.blogapp.entity.Post;
import io.mountblue.blogapp.service.CommentService;
import io.mountblue.blogapp.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;
    @Autowired
    CommentController(CommentService commentService, PostService postService){
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("/saveComment/{postId}")
    public String saveComment(@Valid @ModelAttribute("com") Comment comment,
                              @PathVariable("postId") int postId){
        commentService.saveComment(comment, postId);

        return "redirect:/viewPost?postId="+postId;
    }

    @GetMapping("/editComment/{postId}/{commentId}")
    public String editComment(Model model, @PathVariable("commentId") int commentId,
                              @PathVariable("postId") int postId){
        Comment comment = commentService.getCommentById(commentId);
        Post post = postService.findPostById(postId);

        model.addAttribute("com",comment);
        model.addAttribute("post",post);
        model.addAttribute("postId",postId);

        return "editComment";
    }

    @PostMapping("/updateComment/{postId}/{commentId}")
    public String updateComment(@Valid @ModelAttribute("com") Comment comment,
                                @PathVariable("postId")int postId,
                                @PathVariable("commentId") int commentId){

        comment.setId(commentId);
        commentService.updateComment(comment, postId);

        return "redirect:/viewPost?postId="+postId;
    }

    @GetMapping("/deleteComment/{postId}/{commentId}")
    public String deleteComment(@PathVariable("postId") int postId, @PathVariable("commentId") int commentId){
        commentService.deleteCommentById(commentId);

        return "redirect:/viewPost?postId="+postId;
    }

}
