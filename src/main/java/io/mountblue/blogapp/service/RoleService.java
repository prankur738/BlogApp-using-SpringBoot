package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.User;

public interface RoleService {
    void save(User user, String role);
}
