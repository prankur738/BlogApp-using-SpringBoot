package io.mountblue.blogapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="posts")
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotNull(message = "Title cannot be empty")
    @Column(name="title")
    private String title;

    @NotNull(message = "Excerpt cannot be empty")
    @Column(name="excerpt", length = 1000)
    private String excerpt;

    @NotNull(message = "Content cannot be empty")
    @Column(name="content", columnDefinition = "text")
    private String content;

    @NotNull(message = "Author name cannot be empty")
    @Column(name="author")
    private String author;

    @Column(name="published_at")
    @CreationTimestamp
    private LocalDateTime publishedAt;

    @Column(name="is_published")
    private boolean isPublished = true;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments;

    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name="post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private User user;

}
