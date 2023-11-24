package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.Post;
import io.mountblue.blogapp.entity.Tag;
import io.mountblue.blogapp.repository.CommentRepository;
import io.mountblue.blogapp.repository.PostRepository;
import io.mountblue.blogapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public void updatePost(Post newPost, int postId, String tagString) {
        Post oldPost = findPostById(postId);
        Set<Tag> tags = getTagsFromString(tagString);
        newPost.setId(postId);
        newPost.setTags(tags);
        newPost.setComments(oldPost.getComments());
        newPost.setCreatedAt(oldPost.getCreatedAt());
        newPost.setPublishedAt(oldPost.getPublishedAt());
        newPost.setPublished(oldPost.isPublished());
        postRepository.save(newPost);
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
//        return postRepository.getPostsBySearch(searchText);
        return null;
    }

    @Override
    public Page<Post> getPaginatedPosts(Pageable pageable, List<String> authors, List<String> tags) {
        Page<Post> posts;
        if(authors.isEmpty()){
            authors = null;
        }
        if(tags.isEmpty()){
            tags = null;
        }

        if(authors == null && tags == null){
            posts = postRepository.findAll(pageable);
            System.out.println("In if condition");
            System.out.println("Authors:"+authors);
            System.out.println("Tags: "+tags);
            System.out.println("Posts" + posts);
            return posts;
        }
        posts = postRepository.findByAuthorInAndTags_NameIn(authors, tags, pageable);
        System.out.println("Outside if condition");
        System.out.println("Authors:"+authors);
        System.out.println("Tags: "+tags);
        System.out.println("Posts" + posts);

        return posts;

    }

    @Override
    public Page<Post> getPostsBySearch(Pageable pageable, String searchText) {
        return postRepository.getPostsBySearch(searchText, pageable);
    }

    @Override
    public Set<String> findDistinctAuthors() {

        List<Post> posts = postRepository.findAll();
        Set<String> authors = new HashSet<>();

        for(Post post : posts){
            authors.add(post.getAuthor());
        }

        return authors;
    }
}

