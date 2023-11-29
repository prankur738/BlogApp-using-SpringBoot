package io.mountblue.blogapp.restcontroller;

import io.mountblue.blogapp.entity.Post;
import io.mountblue.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostRestController {

    private final PostService postService;

    @Autowired
    PostRestController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("/posts")
    public ResponseEntity<String> saveNewPost(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestBody Post post, @RequestParam("tags") String tags){
        if(userDetails == null){
            return new ResponseEntity<>("Access Denied",HttpStatus.FORBIDDEN);
        }

        postService.savePost(post, tags);

        return new ResponseEntity<>("New Post added successfully",HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> showAllPosts(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                                   @RequestParam(name="author", required = false)
                                                   String authors,
                                                   @RequestParam(name="tag",required = false) String tags,
                                                   @RequestParam(name="search", required = false) String search,
                                                   @RequestParam(name="sortField", defaultValue = "publishedAt")
                                                   String sortField,
                                                   @RequestParam(name="order",defaultValue = "desc") String order
                                                   ){

        Pageable pageable = PageRequest.of(pageNumber-1,6);

        Page<Post> posts = postService.getPosts(authors, tags, search, sortField, order, pageable);

        List<Post> postList = posts.getContent();

        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> showPostById(@PathVariable("postId") int postId){
        Post post = postService.findPostById(postId);

        if(post==null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<String> updateExistingPostById(@AuthenticationPrincipal UserDetails userDetails,
                                         @PathVariable("postId") int postId, @RequestBody Post post,
                                         @RequestParam("tags") String tags){

        if( postService.findPostById(postId) == null){
            return new ResponseEntity<>("Invalid Post id",HttpStatus.BAD_REQUEST);
        }

        boolean isAuthorized = postService.isUserAuthorized(userDetails, postId);

        if(isAuthorized){
            postService.updatePost(post, postId, tags);
            return new ResponseEntity<>("Post updated successfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Access Denied",HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deleteExistingPostById(@AuthenticationPrincipal UserDetails userDetails,
                                                         @PathVariable("postId") int postId){

        if( postService.findPostById(postId) == null){
            return new ResponseEntity<>("Invalid Post id",HttpStatus.BAD_REQUEST);
        }

        boolean isAuthorized = postService.isUserAuthorized(userDetails, postId);

        if(isAuthorized){
            postService.deletePostById(postId);
            return new ResponseEntity<>("Post deleted successfully",HttpStatus.OK);
        }

        return new ResponseEntity<>("Access Denied",HttpStatus.UNAUTHORIZED);
    }


}
