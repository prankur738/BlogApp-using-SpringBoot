package io.mountblue.blogapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Column(name="title")
    private String title;

    @Column(name="excerpt", length = 1000)
    private String excerpt;

    @Column(name="content", columnDefinition = "text")
    private String content;

    @Column(name="author")
    private String author;

    @Column(name="published_at")
    @CreationTimestamp
    private LocalDateTime publishedAt;

    @Column(name="is_published")
    private boolean isPublished;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments;

    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name="post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();
}
