package com.efrei.olympic_api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.efrei.olympic_api.model.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {

    public List<Ticket> findByEventId(Integer eventId);

    public Integer countTicketsByEventId(Integer id);

}
