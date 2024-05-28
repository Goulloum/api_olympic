package com.efrei.olympic_api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.efrei.olympic_api.model.Role;
import com.efrei.olympic_api.model.RoleEnum;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByName(RoleEnum name);
}
