package io.mountblue.blogapp.controller;

import io.mountblue.blogapp.entity.Comment;
import io.mountblue.blogapp.entity.Post;
import io.mountblue.blogapp.service.CommentService;
import io.mountblue.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    @Autowired
    PostController(PostService postService, CommentService commentService){
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/showAllPosts")
    public String showAllPosts(Model model){
        List<Post> posts = postService.getALlPosts();
        model.addAttribute("posts",posts);
        return  "allPosts";
    }

    @GetMapping("/savePost")
    public String savePost(Model model){
        model.addAttribute("post",new Post());
        return "writePost";
    }

    @PostMapping("/successPage")
    public String success(@ModelAttribute("post") Post post, @RequestParam("tagString") String tagString){
        postService.savePost(post, tagString);
        return "successPage";
    }

    @GetMapping("/viewPost/{postId}")
    public String viewPost(Model model, @PathVariable("postId") int id){
        Post post = postService.findPostById(id);
        model.addAttribute("post",post);
        model.addAttribute("com",new Comment());
        return "viewPost";
    }

}
