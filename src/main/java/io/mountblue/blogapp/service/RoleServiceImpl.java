package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.Role;
import io.mountblue.blogapp.entity.User;
import io.mountblue.blogapp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;
    @Autowired
    RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public void save(User user, String roleName){
        String username = user.getUsername();
        Role role = new Role();
        role.setUsername(username);
        role.setRole(roleName);
        roleRepository.save(role);
    }
}
