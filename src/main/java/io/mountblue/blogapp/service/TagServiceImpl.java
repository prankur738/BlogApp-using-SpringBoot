package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.Tag;
import io.mountblue.blogapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagServiceImpl implements TagService{
   private final TagRepository tagRepository;
   @Autowired
   TagServiceImpl(TagRepository tagRepository){
       this.tagRepository = tagRepository;
   }
    @Override
    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }
}
