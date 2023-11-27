package io.mountblue.blogapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="username")
    private String username;

    @Column(name = "role")
    private String role;

//    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
//            mappedBy = "role")
//    private List<User> users = new ArrayList<>();

    public Role() {
    }

//    public Role(String name) {
//        this.name = name;
//    }
}