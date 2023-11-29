package io.mountblue.blogapp.restcontroller;

import io.mountblue.blogapp.entity.Post;
import io.mountblue.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Post>> showAllPosts(){

        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
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

        boolean isAuthorized = isUserAuthorized(userDetails, postId);

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

        boolean isAuthorized = isUserAuthorized(userDetails,postId);

        if(isAuthorized){
            postService.deletePostById(postId);
            return new ResponseEntity<>("Post deleted successfully",HttpStatus.OK);
        }

        return new ResponseEntity<>("Access Denied",HttpStatus.UNAUTHORIZED);
    }

    private boolean isUserAuthorized(UserDetails userDetails, int postId){
        Post post = postService.findPostById(postId);
        boolean isAuthorized = false;

        if( userDetails==null){
            return false;
        }
        else if(userDetails.getAuthorities().toString().contains("ROLE_ADMIN")){
            isAuthorized = true;
        } else if (userDetails.getUsername().equals(post.getAuthor())) {
            isAuthorized = true;
        }

        return isAuthorized;
    }

}
