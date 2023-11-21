//package io.mountblue.blogapp.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Getter @Setter
//@Entity
//@Table
//public class Comment {
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private int id;
//    private String name;
//    private String email;
//    private String comment;
//    @ManyToOne
//    private Post postId;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//}
