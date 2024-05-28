package com.efrei.olympic_api.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.efrei.olympic_api.enums.RoleEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UpdateUserDto {
    @Email
    @NotEmpty
    private String email;

    @Length(min = 8)
    @NotEmpty
    private String password;

    @NotEmpty
    private String fullName;

    @NotEmpty
    private List<RoleEnum> roles;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEnum> roles) {
        this.roles = roles;
    }

}
