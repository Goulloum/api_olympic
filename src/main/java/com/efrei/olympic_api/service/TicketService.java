package com.efrei.olympic_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efrei.olympic_api.dto.CreateTicketDto;
import com.efrei.olympic_api.dto.UpdateTicketDto;
import com.efrei.olympic_api.enums.EntityEnum;
import com.efrei.olympic_api.exception.RessourceNotFoundException;
import com.efrei.olympic_api.model.Event;
import com.efrei.olympic_api.model.Ticket;
import com.efrei.olympic_api.model.User;
import com.efrei.olympic_api.repository.EventRepository;
import com.efrei.olympic_api.repository.TicketRepository;
import com.efrei.olympic_api.repository.UserRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    public List<Ticket> allTickets() {
        List<Ticket> tickets = new ArrayList<Ticket>();

        ticketRepository.findAll().forEach(tickets::add);

        return tickets;
    }

    public Ticket createTicket(CreateTicketDto ticket) {

        // Ensure user exist
        Optional<User> user = userRepository.findById(ticket.getUserId());
        if (user.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.USER);
        }
        // Ensure event exist
        Optional<Event> event = eventRepository.findById(ticket.getEventId());
        if (event.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.EVENT);
        }

        Ticket newTicket = new Ticket();
        newTicket.setPrice(ticket.getPrice());
        newTicket.setUser(user.get());
        newTicket.setEvent(event.get());
        newTicket.setOwnerFullName(ticket.getOwnerFullName());
        newTicket.setIsInsured(ticket.getIsInsured());

        return ticketRepository.save(newTicket);

    }

    public Optional<Ticket> getTicketById(Integer id) {

        return ticketRepository.findById(id);
    }

    public Boolean deleteTicket(Integer id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);

        if (ticket.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.TICKET);
        }

        ticketRepository.deleteById(id);
        return true;
    }

    public Boolean updateTicket(Integer id, UpdateTicketDto ticket) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);

        if (ticketOptional.isEmpty()) {
            return false;
        }

        // Ensure new user exist
        Optional<User> user = userRepository.findById(ticket.getUserId());
        if (user.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.USER);
        }
        // Ensure new event exist
        Optional<Event> event = eventRepository.findById(ticket.getEventId());
        if (event.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.EVENT);
        }

        Ticket updatedTicket = ticketOptional.get();
        updatedTicket.setPrice(ticket.getPrice());
        updatedTicket.setOwnerFullName(ticket.getOwnerFullName());
        updatedTicket.setUser(user.get());
        updatedTicket.setEvent(event.get());
        updatedTicket.setIsInsured(ticket.getIsInsured());

        ticketRepository.save(updatedTicket);

        return true;
    }

}
