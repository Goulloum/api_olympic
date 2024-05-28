package com.efrei.olympic_api.bootstrap;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.efrei.olympic_api.enums.RoleEnum;
import com.efrei.olympic_api.model.User;
import com.efrei.olympic_api.repository.RoleRepository;
import com.efrei.olympic_api.repository.UserRepository;

@Component
public class UserSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadAdminUser();
        this.loadUser();
    }

    public void loadAdminUser() {
        Optional<User> admin = userRepository.findByEmail("admin@test.com");

        if (admin.isEmpty()) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setPassword(bCryptPasswordEncoder.encode("admin"));
            user.setRoles(List.of(roleRepository.findByName(RoleEnum.ADMIN).get()));
            user.setFullName("Admin");
            userRepository.save(user);
        }

    }

    public void loadUser() {
        Optional<User> user = userRepository.findByEmail("user@test.com");

        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setEmail("user@test.com");
            newUser.setPassword(bCryptPasswordEncoder.encode("user"));
            newUser.setRoles(List.of(roleRepository.findByName(RoleEnum.USER).get()));
            newUser.setFullName("User");
            userRepository.save(newUser);

        }

    }

}
