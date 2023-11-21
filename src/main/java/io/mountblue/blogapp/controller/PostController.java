package io.mountblue.blogapp.controller;

import io.mountblue.blogapp.entity.Post;
import io.mountblue.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {

    @Autowired
    PostService service;

    @GetMapping("/showAllPosts")
    public String showAllPosts(Model model){
        List<Post> posts = service.getALlPosts();
        model.addAttribute("posts",posts);
        return  "allPosts";
    }

    @GetMapping("/savePost")
    public String savePost(Model model){
        model.addAttribute("post",new Post());
        return "writePost";
    }

    @PostMapping("/successPage")
    public String success(@ModelAttribute("post") Post post){
        service.savePost(post);
        return "successPage";
    }

    @GetMapping("/viewPost/{postId}")
    public String viewPost(Model model, @PathVariable("postId") int id){
        Post post = service.findById(id);
        model.addAttribute("post",post);
        return "viewPost";
    }


}
