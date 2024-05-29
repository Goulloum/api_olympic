package com.efrei.olympic_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efrei.olympic_api.dto.CreateEventDto;
import com.efrei.olympic_api.dto.PurchaseTicketDto;
import com.efrei.olympic_api.dto.UpdateEventDto;
import com.efrei.olympic_api.enums.EntityEnum;
import com.efrei.olympic_api.exception.EventOverlapseException;
import com.efrei.olympic_api.exception.NotEnoughTicketException;
import com.efrei.olympic_api.exception.RessourceNotFoundException;
import com.efrei.olympic_api.model.Event;
import com.efrei.olympic_api.model.Stadium;
import com.efrei.olympic_api.model.Ticket;
import com.efrei.olympic_api.model.User;
import com.efrei.olympic_api.repository.EventRepository;
import com.efrei.olympic_api.repository.StadiumRepository;
import com.efrei.olympic_api.repository.TicketRepository;
import com.efrei.olympic_api.repository.UserRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public List<Event> allEvents() {
        ArrayList<Event> events = new ArrayList<Event>();

        eventRepository.findAll().forEach(events::add);

        return events;

    }

    public Event getEventById(Integer id) {

        Optional<Event> event = eventRepository.findById(id);

        if (event.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.EVENT);
        }

        return event.get();
    }

    public Event createEvent(CreateEventDto event) {

        // Ensure Stadium exist
        Optional<Stadium> stadium = stadiumRepository.findById(event.getStadiumId());

        if (stadium.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.STADIUM);
        }

        Event newEvent = new Event();
        newEvent.setName(event.getName());
        newEvent.setDateTime(event.getDateTime());
        newEvent.setDurationInMinute(event.getDurationInMinute());
        newEvent.setTicketUnitPrice(event.getTicketUnitPrice());
        newEvent.setTicketsAvailable(event.getTicketsAvailable());
        newEvent.setIsActive(event.getIsActive());
        newEvent.setStadium(stadium.get());

        return eventRepository.save(newEvent);
    }

    public Boolean deleteEvent(Integer id) {
        Optional<Event> event = eventRepository.findById(id);

        if (event.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.EVENT);
        }

        eventRepository.delete(event.get());

        return true;
    }

    public Boolean updateEvent(Integer id, UpdateEventDto event) {
        // Ensure Event exist
        Optional<Event> eventOptional = eventRepository.findById(id);

        if (eventOptional.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.EVENT);
        }

        // Ensure Stadium exist
        Optional<Stadium> stadium = stadiumRepository.findById(event.getStadiumId());

        if (stadium.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.STADIUM);
        }

        Event updatedEvent = eventOptional.get();
        updatedEvent.setName(event.getName());
        updatedEvent.setDateTime(event.getDateTime());
        updatedEvent.setDurationInMinute(event.getDurationInMinute());
        updatedEvent.setIsActive(event.getIsActive());
        updatedEvent.setTicketUnitPrice(event.getTicketUnitPrice());
        updatedEvent.setTicketsAvailable(event.getTicketsAvailable());
        updatedEvent.setStadium(stadium.get());

        eventRepository.save(updatedEvent);

        return true;
    }

    public Ticket purchaseTicket(Integer id, PurchaseTicketDto purchaseTicketDto) {
        // Unsure event exist
        Optional<Event> event = eventRepository.findById(id);

        if (event.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.EVENT);
        }

        // Unsure user exist

        Optional<User> user = userRepository.findById(purchaseTicketDto.getUserId());

        if (user.isEmpty()) {
            throw new RessourceNotFoundException(EntityEnum.USER);
        }

        //Unsure there are enough tickets available 

        Integer purchasedTickets = ticketRepository.sumQuantityByEventId(id);

        if (purchasedTickets + purchaseTicketDto.getQuantity() > event.get().getTicketsAvailable()) {
            throw new NotEnoughTicketException(event.get(), event.get().getTicketsAvailable() - purchasedTickets);
        }


        // Unsure user doesn't already have a ticket for an event at the same time
        List<Ticket> tickets = user.get().getTickets();

        for (Ticket ticket : tickets) {
            // Get start and end time of event
            long eventStartTime = event.get().getDateTime().getTime();
            long eventEndTime = eventStartTime + event.get().getDurationInMinute() * 60000;

            // Get start and end time of ticket
            long ticketStartTime = ticket.getEvent().getDateTime().getTime();
            long ticketEndTime = ticketStartTime + ticket.getEvent().getDurationInMinute() * 60000;

            // Check if the ticket is for an event at the same time
            if ((ticketStartTime >= eventStartTime && ticketStartTime <= eventEndTime)
                    || (ticketEndTime >= eventStartTime && ticketEndTime <= eventEndTime)) {
                throw new EventOverlapseException(ticket.getEvent(), event.get());
            }
        }

        Ticket ticket = new Ticket();
        if (purchaseTicketDto.getOwnerFullName() != null) {
            ticket.setOwnerFullName(purchaseTicketDto.getOwnerFullName());
        } else {
            ticket.setOwnerFullName(user.get().getFullName());
        }

        /*
         * Check for reduction by quantity
         * 2 tickets = 10% reduction
         * 5 tickets = 20% reduction
         * 10 tickets = 30% reduction
         */
        int quantity = purchaseTicketDto.getQuantity();
        double totalPrice = event.get().getTicketUnitPrice() * quantity;

        if (quantity >= 2 && quantity < 5) {
            totalPrice = totalPrice - (totalPrice * 0.1);
        } else if (quantity >= 5 && quantity < 10) {
            totalPrice = totalPrice - (totalPrice * 0.2);
        } else if (quantity >= 10) {
            totalPrice = totalPrice - (totalPrice * 0.3);
        }

        ticket.setPrice(totalPrice);

        ticket.setIsInsured(purchaseTicketDto.getIsInsured());
        ticket.setQuantity(purchaseTicketDto.getQuantity());
        ticket.setUser(user.get());
        ticket.setEvent(event.get());

        return ticketRepository.save(ticket);

    }

}
