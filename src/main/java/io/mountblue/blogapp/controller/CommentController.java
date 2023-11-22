package io.mountblue.blogapp.controller;

import io.mountblue.blogapp.entity.Comment;
import io.mountblue.blogapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    private final CommentService commentService;

    @Autowired
    CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/saveComment/{postId}")
    public String saveComment(@ModelAttribute("com") Comment comment, @PathVariable("postId") int id){
        commentService.saveComment(comment, id);
        return "redirect:/viewPost/"+id;
    }

}
