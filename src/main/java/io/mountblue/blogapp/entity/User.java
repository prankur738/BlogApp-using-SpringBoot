package io.mountblue.blogapp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter @Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Email cannot be empty")
    @Column(name = "username")
    private String username;

    @NotNull(message = "Password cannot be empty")
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled = true;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

}
