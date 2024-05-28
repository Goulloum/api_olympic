package com.efrei.olympic_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.efrei.olympic_api.model.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {

}
