package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.User;

public interface UserService {

    void saveUser(User user);
    boolean isUserExists(String username);

}
