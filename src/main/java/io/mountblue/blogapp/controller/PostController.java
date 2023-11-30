package io.mountblue.blogapp.controller;

import io.mountblue.blogapp.entity.Comment;
import io.mountblue.blogapp.entity.Post;
import io.mountblue.blogapp.entity.Tag;
import io.mountblue.blogapp.service.PostService;
import io.mountblue.blogapp.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
public class PostController {
    private final PostService postService;
    private final TagService tagService;
    @Autowired
    PostController(PostService postService, TagService tagService){
        this.postService = postService;
        this.tagService = tagService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class,trimmerEditor);
    }

    @GetMapping("/newPost")
    public String showNewPostPage(Model model, @AuthenticationPrincipal UserDetails userDetails){

        if(userDetails == null){ // non-logged in user
            return "accessDenied";
        }

        Post post = new Post();
        post.setAuthor(userDetails.getUsername());

        model.addAttribute("post",post);

        return "newPost";
    }

    @PostMapping("/savePost")
    public String savePost(@Valid @ModelAttribute("post") Post post,
                           BindingResult bindingResult,
                           @RequestParam(name = "tagString", required = false) String tagString){

        if(bindingResult.hasErrors()){
            return "newPost";
        }
        else{
            postService.savePost(post, tagString);
            return "redirect:/";
        }
    }

    @GetMapping("/viewPost/{postId}")
    public String viewPost(Model model, @PathVariable("postId") Integer postId){

        Post post = postService.findPostById(postId);

        model.addAttribute("post",post);
        model.addAttribute("newComment",new Comment());

        return "viewPost";
    }

    @GetMapping ("/editPost/{postId}")
    public String showEditPostPage(Model model,
                                   @PathVariable("postId") Integer postId,
                                   @AuthenticationPrincipal UserDetails userDetails){

        Post post = postService.findPostById(postId);

        boolean userAuthorized = postService.isUserAuthorized(userDetails, postId);

        if(userAuthorized){
            String tagString = postService.getCommaSeperatedTags(postId);

            model.addAttribute("post",post);
            model.addAttribute("tagString", tagString);

            return "editPost";
        }

        return "accessDenied";
    }

    @PostMapping("/updatePost/{postId}")
    public String updatePost(@Valid @ModelAttribute("post") Post updatedPost,
                             BindingResult bindingResult,
                             @PathVariable("postId") Integer postId,
                             @RequestParam(name="tagString", required = false) String tagString){

        if(bindingResult.hasErrors()){
            return "editPost";
        }
        else{
            postService.updatePost(updatedPost, postId, tagString);
            return "redirect:/viewPost/" + postId;
        }

    }

    @PostMapping("/deletePost/{postId}")
    public String deletePost(@AuthenticationPrincipal UserDetails userDetails,
                             @PathVariable("postId") Integer postId){

        boolean userAuthorized = postService.isUserAuthorized(userDetails, postId);

        if(userAuthorized){
            postService.deletePostById(postId);
            return "redirect:/";
        }

        return "accessDenied";
    }

    @RequestMapping ("/")
    public String showAllPosts(
            @RequestParam(name = "page", defaultValue = "1") Integer pageNumber,
            @RequestParam(name="author", required = false) String authors,
            @RequestParam(name="tag",required = false) String tags,
            @RequestParam(name="search", required = false) String search,
            @RequestParam(name="sortField", defaultValue = "publishedAt") String sortField,
            @RequestParam(name="order",defaultValue = "desc") String order,
            Model model){

        Pageable pageable = PageRequest.of(pageNumber-1,6);

        Page<Post> posts = postService.getPosts(authors, tags, search, sortField, order, pageable);

        search = (search == null) ? "" : search;
        authors = (authors == null) ? "" : authors;
        tags = (tags == null) ? "" : tags;

        model.addAttribute("search", search);
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        model.addAttribute("posts",posts);
        model.addAttribute("totalPages",posts.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("order", order);
        model.addAttribute("currentPage",pageNumber);

        return  "allPosts";
    }
    @GetMapping("/filterPosts")
    public String showFilterPostsPage(Model model){

        Set<String> authors = postService.findDistinctAuthors();
        List<Tag> tags = tagService.getAllTags();

        model.addAttribute("authors",authors);
        model.addAttribute("tags",tags);

        return "filterPosts";
    }

}
