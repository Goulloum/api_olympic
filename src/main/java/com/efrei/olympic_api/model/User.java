package com.efrei.olympic_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

}
