package io.mountblue.blogapp.service;

import io.mountblue.blogapp.entity.Role;
import io.mountblue.blogapp.entity.User;
import io.mountblue.blogapp.repository.RoleRepository;
import io.mountblue.blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                    PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void saveUser(User user) {
        String username = user.getUsername();
        String password = passwordEncoder.encode(user.getPassword());

        user.setPassword(password);

        Role role = new Role();
        role.setUsername(username);
        role.setRole("ROLE_AUTHOR");

        roleRepository.save(role);
        userRepository.save(user);
    }

    @Override
    public boolean isUserExists(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        return user.isPresent();
    }
}
