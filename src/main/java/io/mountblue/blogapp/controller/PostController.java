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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public String newPost(Model model){
        model.addAttribute("post",new Post());
        return "newPost";
    }
    @GetMapping ("/editPost")
    public String editPost(Model model, @RequestParam("postId") int postId){
        Post post = postService.findPostById(postId);
        model.addAttribute("post",post);
        String tagString = postService.getCommaSeperatedTags(postId);
        model.addAttribute("tagString", tagString);
        return "editPost";
    }
    @GetMapping("/viewPost")
    public String viewPost(Model model, @RequestParam("postId") int postId){
        Post post = postService.findPostById(postId);
        model.addAttribute("post",post);
        model.addAttribute("newComment",new Comment());
        return "viewPost";
    }

    @GetMapping("/")
    public String showAllPosts(
            @RequestParam(name = "page", defaultValue = "1") int pageNumber,
            @RequestParam(name="author", required = false) List<String> authorList,
            @RequestParam(name="tag",required = false) List<String> tagList,
            @RequestParam(name="search", required = false) String search,
            @RequestParam(name="sortField", defaultValue = "publishedAt") String sortField,
            @RequestParam(name="order",defaultValue = "desc") String order,
            Model model){
        //current page and items per page
        Pageable pageable = PageRequest.of(pageNumber-1,9);
        System.out.println(authorList);
        System.out.println(tagList);
        System.out.println(search);
        if (authorList == null) {
            authorList = Collections.emptyList(); // Set to an empty list or provide default values
        }
        if (tagList == null) {
            tagList = Collections.emptyList(); // Set to an empty list or provide default values
        }
        search = search==null ? "" : search;
        Page<Post> posts = postService.getPosts(authorList, tagList, search, sortField, order,model, pageable);

        String  authors = String.join("&author=", authorList);
        String  tags = String.join("&tag=", tagList);



        model.addAttribute("search", search);
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        model.addAttribute("posts",posts);
        model.addAttribute("totalPages",posts.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("order", order);
        model.addAttribute("currentPage",pageNumber);
        return  "allPosts";

//        if (authorList == null) {
//            authorList = Collections.emptyList(); // Set to an empty list or provide default values
//        }
//        if (tagList == null) {
//            tagList = Collections.emptyList(); // Set to an empty list or provide default values
//        }
//
//        if(search != null){
//            posts = postService.getPostsBySearch(search, pageable);
//        }
//        else{
//            posts = postService.getPaginatedPosts(pageable,authorList,tagList);
//            search="";
//        }
//        System.out.println("Total pages = "+posts.getTotalPages());
//        System.out.println("Current Page = "+ pageNumber);
//
//        String  authors = String.join("&author=", authorList);
//        String  tags = String.join("&tag=", tagList);
//
//        model.addAttribute("search", search);
//        model.addAttribute("authors", authors);
//        model.addAttribute("tags", tags);
//        model.addAttribute("posts",posts);
//        model.addAttribute("currentPage",pageNumber);
//        model.addAttribute("totalPages",posts.getTotalPages());



        /*
        model.addAttribute("currentPage",pageNumber);

        return  "allPosts";
         */
    }

    @GetMapping("/successPage")
    public String success(@Valid @ModelAttribute("post") Post post, BindingResult bindingResult,
                          @RequestParam(name = "tagString",required = false) String tagString){
        if(bindingResult.hasErrors()){
            return "newPost";
        }
        else{
            postService.savePost(post, tagString);
            return "redirect:/";
        }
    }
    @PostMapping("/updatePost/{postId}")
    public String updatePost(@Valid @ModelAttribute("post") Post post,BindingResult bindingResult ,
                             @PathVariable("postId") int postId,
                             @RequestParam(name="tagStr", required = false) String tagStr){
        if(bindingResult.hasErrors()){
            return "editPost";
        }
        else{
            postService.updatePost(post, postId, tagStr);
            return "redirect:/viewPost?postId="+postId;
        }
    }

    @GetMapping("/deletePost")
    public String deletePost(@RequestParam("postId") int postId){
        postService.deletePostById(postId);
        return "redirect:/";
    }
    @GetMapping("/filter")
    public String filterPosts(Model model){
        Set<String> authors = postService.findDistinctAuthors();
        List<Tag> tags = tagService.getAllTags();


        model.addAttribute("authors",authors);
        model.addAttribute("tags",tags);

        return "filterPage";
    }

    //
//    @GetMapping("/showAllPosts/search/{pageNumber}")
//    public String showSearchedPosts(@PathVariable("pageNumber") int pageNumber, Model model, @RequestParam(name="searchText") String searchText){
//        Pageable pr = PageRequest.of(pageNumber-1, 9);
//        Page<Post> posts = postService.getPostsBySearch(searchText, pr);
//        model.addAttribute("posts",posts);
//        model.addAttribute("currentPage",pageNumber);
//        model.addAttribute("totalPages",posts.getTotalPages());
//        return "allPosts";
//    }

}
