package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.Post;
import io.mountblue.blogapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepository repository;
    @Autowired
    PostServiceImpl(PostRepository repo){
        this.repository = repo;
    }
    @Override
    public void savePost(Post post) {
        repository.save(post);
    }

    @Override
    public void updatePost(int id) {
        Post post = findById(id);
        repository.save(post);
    }

    @Override
    public Post findById(int id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Post> getALlPosts() {
        List<Post> posts = repository.findAll();
        return posts;
    }
}
