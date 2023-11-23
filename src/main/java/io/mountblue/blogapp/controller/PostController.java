package io.mountblue.blogapp.controller;

import io.mountblue.blogapp.entity.Comment;
import io.mountblue.blogapp.entity.Post;
import io.mountblue.blogapp.service.CommentService;
import io.mountblue.blogapp.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
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

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class,trimmerEditor);
    }

    @GetMapping("/showAllPosts")
    public String showAllPosts(Model model){
        List<Post> posts = postService.getALlPosts();
        model.addAttribute("posts",posts);
        return  "allPosts";
    }

    @GetMapping("/showAllPosts/search")
    public String showSearchedPosts(Model model, @RequestParam(name="searchText") String searchText){
        List<Post> posts = postService.getPostsBySearch(searchText);
        model.addAttribute("posts",posts);
        return "allPosts";
    }

    @GetMapping("/savePost")
    public String savePost(Model model){
        model.addAttribute("post",new Post());
        return "writePost";
    }

    @PostMapping("/successPage")//@Valid @RequestParam("tagString") String tagString,
    public String success(@Valid @ModelAttribute("post") Post post, BindingResult bindingResult,
                          @RequestParam(name = "tagString",required = false) String tagString){
        if(bindingResult.hasErrors()){
            return "writePost";
        }
        else{
            postService.savePost(post, tagString);
            return "successPage";
        }
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
    public String updatePost(@Valid @ModelAttribute("post") Post post,BindingResult bindingResult ,@PathVariable("postId") int postId,
                             @RequestParam(name="tagStr", required = false) String tagStr){
        if(bindingResult.hasErrors()){
            return "editPost";
        }
        else{
            post.setId(postId);
            postService.savePost(post, tagStr);
            return "successPage";
        }
    }

    @GetMapping("/deletePost/{postId}")
    public String deletePost(@PathVariable("postId") int postId){
        postService.deletePostById(postId);
        return "redirect:/showAllPosts";
    }

}
