package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.Comment;
import io.mountblue.blogapp.entity.Post;
import io.mountblue.blogapp.entity.Tag;
import io.mountblue.blogapp.repository.CommentRepository;
import io.mountblue.blogapp.repository.PostRepository;
import io.mountblue.blogapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;


    @Autowired
    PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository, TagRepository tagRepository){
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.tagRepository = tagRepository;
    }
    @Override
    public void savePost(Post post, String tagString) {
        String[] tagNames = tagString.split(",");

        List<Tag> tags = new ArrayList<>();

        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByName(tagName.trim())
                    .orElseGet(() -> new Tag(tagName.trim()));
            tags.add(tag);
        }

        post.setTags(tags);

        postRepository.save(post);
    }

    @Override
    public void updatePost(int id, String tagString) {
        Post post = findPostById(id);
        String[] tagNames = tagString.split(",");

        List<Tag> tags = new ArrayList<>();

        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByName(tagName.trim())
                    .orElseGet(() -> new Tag(tagName.trim()));
            tags.add(tag);
        }

        post.setTags(tags);
        postRepository.save(post);
    }

    @Override
    public Post findPostById(int id) {
        Optional<Post> postOptional = postRepository.findById(id);
        return postOptional.orElse(null);
    }

    @Override
    public List<Post> getALlPosts() {
        return postRepository.findAll();
    }

    @Override
    public String getCommaSeperatedTags(int id) {
        Post post = findPostById(id);
        String tagString = "";

        for(Tag tag : post.getTags()){
            tagString += tag.getName()+", ";
        }
        return tagString;
    }

    @Override
    public void deletePostById(int id) {
        postRepository.deleteById(id);
    }
}

