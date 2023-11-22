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

    @GetMapping("/editPost/{postId}")
    public String editPost(Model model, @PathVariable("postId") int id){
        Post post = postService.findPostById(id);
        model.addAttribute("post",post);
        String tagString = postService.getCommaSeperatedTags(id);
        model.addAttribute("tagString", tagString);
        return "editPost";
    }

    @PostMapping("/updatePost/{postId}")
    public String updatePost(@ModelAttribute("post") Post post, @PathVariable("postId") int postId, @ModelAttribute("tagString") String tagString, @RequestParam("tagStr") String tagStr){
        post.setId(postId);
        System.out.println("Tags: " + tagString +" :::::::: "+tagStr);
        postService.savePost(post, tagStr);
        return "successPage";
    }

    @GetMapping("/deletePost/{postId}")
    public String deletePost(@PathVariable("postId") int postId){
        postService.deletePostById(postId);
        return "redirect:/showAllPosts";
    }

}
