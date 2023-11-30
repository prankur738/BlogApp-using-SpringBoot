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
    public String saveNewComment(@Valid @ModelAttribute("newComment") Comment newComment,
                                 @PathVariable("postId") Integer postId){

        commentService.saveComment(newComment, postId);

        return "redirect:/viewPost/" + postId;
    }

    @GetMapping("/editComment/{postId}/{commentId}")
    public String showEditCommentPage(Model model,
                              @PathVariable("commentId") Integer commentId,
                              @PathVariable("postId") Integer postId){

        Comment comment = commentService.getCommentById(commentId);
        Post post = postService.findPostById(postId);

        model.addAttribute("editedComment",comment);
        model.addAttribute("post",post);
        model.addAttribute("postId",postId);

        return "editComment";
    }

    @PostMapping("/updateComment/{postId}/{commentId}")
    public String updateComment(@Valid @ModelAttribute("editedComment") Comment editedComment,
                                @PathVariable("postId") Integer postId,
                                @PathVariable("commentId") Integer commentId){

        editedComment.setId(commentId);
        commentService.updateComment(editedComment, postId);

        return "redirect:/viewPost/" + postId;
    }

    @GetMapping("/deleteComment/{postId}/{commentId}")
    public String deleteComment(@PathVariable("postId") Integer postId,
                                @PathVariable("commentId") Integer commentId){

        commentService.deleteCommentById(commentId);

        return "redirect:/viewPost/" + postId;
    }

}
