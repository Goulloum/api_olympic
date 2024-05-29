package com.efrei.olympic_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.efrei.olympic_api.model.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {

    public List<Ticket> findByEventId(Integer eventId);

    @Query(value = "SELECT SUM(quantity) FROM tickets WHERE event_id = ?1", nativeQuery = true)
    public Integer sumQuantityByEventId(Integer id);

}
