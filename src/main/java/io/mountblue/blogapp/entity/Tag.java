//package io.mountblue.blogapp.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import java.time.LocalDateTime;
//import java.util.List;
//@Getter @Setter
//@Entity
//@Table//(name="tag")
//public class Tag {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    @Column(name="id")
//    private int id;
////    @Column(name="name")
//    private String name;
////    @Column(name="created_at")
//    private LocalDateTime createdAt;
////    @Column(name="updated_at")
//    private LocalDateTime updatedAt;
//    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
//    mappedBy = "tags")
//    private List<Post> posts;
//
//
//}
