package com.efrei.olympic_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efrei.olympic_api.dto.CreateUserDto;
import com.efrei.olympic_api.dto.UpdateUserDto;
import com.efrei.olympic_api.model.User;
import com.efrei.olympic_api.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.allUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserDto user) {
        User newUser = userService.createUser(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        boolean isDeleted = userService.deleteUser(id);

        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UpdateUserDto user) {
        boolean isUpdated = userService.updateUser(id, user);

        if (isUpdated) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}