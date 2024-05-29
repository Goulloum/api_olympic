package com.efrei.olympic_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.efrei.olympic_api.model.Stadium;

@Repository
public interface StadiumRepository extends CrudRepository<Stadium, Integer> {

}
