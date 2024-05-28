package com.efrei.olympic_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.efrei.olympic_api.dto.LoginUserDto;
import com.efrei.olympic_api.dto.RegisterUserDto;
import com.efrei.olympic_api.model.Role;
import com.efrei.olympic_api.model.RoleEnum;
import com.efrei.olympic_api.model.User;
import com.efrei.olympic_api.repository.RoleRepository;
import com.efrei.olympic_api.repository.UserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User signup(RegisterUserDto input) {
        Optional<Role> userRole = roleRepository.findByName(RoleEnum.USER);

        if (userRole.isEmpty()) {
            throw new RuntimeException("User role not found");
        }

        User user = new User();

        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRoles(List.of(userRole.get()));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}