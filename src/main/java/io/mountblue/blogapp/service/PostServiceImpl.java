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
        Set<Tag> tags = getTagsFromString(tagString);
        post.setTags(tags);

        postRepository.save(post);
    }

    @Override
    public void updatePost(int id, String tagString) {
        Post post = findPostById(id);
        Set<Tag> tags = getTagsFromString(tagString);

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

    private Set<Tag> getTagsFromString(String tagString){
        Set<Tag> tags = new HashSet<>();

        if(tagString != null){

            String[] tagNames = tagString.split(",");

            for (String tagName : tagNames) {

                String tag = tagName.trim();
                Optional<Tag> tagOptional = tagRepository.findByName(tag);

                if(tagOptional.isPresent()){
                    tags.add(tagOptional.get());
                }
                else{
                    Tag newTag = new Tag(tag);
                    tagRepository.save(newTag);
                    tags.add(newTag);
                }
            }
        }
        return tags;
    }

    @Override
    public List<Post> getPostsBySearch(String searchText) {
        return postRepository.getPostsBySearch(searchText);
    }
}

