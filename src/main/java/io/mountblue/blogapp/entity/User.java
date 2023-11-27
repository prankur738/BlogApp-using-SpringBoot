package io.mountblue.blogapp.entity;


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

//    @NotNull(message = "Name cannot be empty")
//    @Column(name="name")
//    private String name;

    @NotNull(message = "Email cannot be empty")
    @Column(name = "username")
    private String username;

    @NotNull(message = "Password cannot be empty")
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled = true;

//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
//    private Role role;

//    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
//    @JoinTable(name = "users_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private List<Role> roles = new ArrayList<>();

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    public User() {
    }
//    public User(String name, String email, String password, boolean enabled) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.enabled = enabled;
//    }
//
//    public User(String name, String email, String password, boolean enabled,
//                List<Role> roles) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.enabled = enabled;
//        this.roles = roles;
//    }

}
