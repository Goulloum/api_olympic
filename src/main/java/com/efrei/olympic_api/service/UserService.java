package com.efrei.olympic_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.efrei.olympic_api.dto.CreateUserDto;
import com.efrei.olympic_api.dto.UpdateUserDto;
import com.efrei.olympic_api.enums.EntityEnum;
import com.efrei.olympic_api.exception.RessourceNotFoundException;
import com.efrei.olympic_api.model.Role;
import com.efrei.olympic_api.model.User;
import com.efrei.olympic_api.repository.RoleRepository;
import com.efrei.olympic_api.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);
        ;

        return users;
    }

    public User getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.USER);
        }

        return user.get();
    }

    public User createUser(@Valid CreateUserDto user) {

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newUser.setFullName(user.getFullName());

        ArrayList<Role> newRoles = new ArrayList<>();
        user.getRoles().forEach(role -> {
            roleRepository.findByName(role).ifPresent(newRoles::add);
        });
        newUser.setRoles(newRoles);

        return userRepository.save(newUser);
    }

    public boolean deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.USER);
        }

        userRepository.delete(user.get());
        return true;
    }

    public boolean updateUser(Integer id, UpdateUserDto user) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.USER);
        }

        User updatedUser = userOptional.get();
        updatedUser.setEmail(user.getEmail());
        updatedUser.setFullName(user.getFullName());
        updatedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        ArrayList<Role> newRoles = new ArrayList<>();
        user.getRoles().forEach(role -> {
            roleRepository.findByName(role).ifPresent(newRoles::add);
        });
        updatedUser.setRoles(newRoles);

        userRepository.save(updatedUser);

        return true;
    }

}
