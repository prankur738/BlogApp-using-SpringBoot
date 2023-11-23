package io.mountblue.blogapp.repository;

import io.mountblue.blogapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
//
//    @Query("SELECT p FROM Post p LEFT JOIN p.tags t WHERE "+
//            "t.name LIKE CONCAT('%', :query, '%') OR "+
//            "p.title LIKE CONCAT('%', :query, '%') OR "+
//            "p.author LIKE CONCAT('%', :query, '%') OR "+
//            "p.content LIKE CONCAT('%', :query, '%') OR "+
//            "p.excerpt LIKE CONCAT('%', :query, '%')"
//    )
//    List<Post> getPostsBySearch(String query);
//
    @Query("SELECT p FROM Post p LEFT JOIN p.tags t WHERE "+
            "t.name LIKE CONCAT('%', :query, '%') OR "+
            "p.title LIKE CONCAT('%', :query, '%') OR "+
            "p.author LIKE CONCAT('%', :query, '%') OR "+
            "p.content LIKE CONCAT('%', :query, '%') OR "+
            "p.excerpt LIKE CONCAT('%', :query, '%')"
    )
    Page<Post> getPostsBySearch(String query, Pageable pageable);





}

