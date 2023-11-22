package io.mountblue.blogapp.repository;

import io.mountblue.blogapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
