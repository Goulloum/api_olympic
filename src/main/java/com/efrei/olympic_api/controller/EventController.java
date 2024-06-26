package com.efrei.olympic_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efrei.olympic_api.dto.CreateEventDto;
import com.efrei.olympic_api.dto.PurchaseTicketDto;
import com.efrei.olympic_api.dto.UpdateEventDto;
import com.efrei.olympic_api.model.Event;
import com.efrei.olympic_api.model.Ticket;
import com.efrei.olympic_api.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
@PreAuthorize("hasRole('ADMIN')")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> findAllEvents() {

        List<Event> events = eventService.allEvents();

        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> findById(@PathVariable Integer id) {

        Event event = eventService.getEventById(id);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody CreateEventDto event) {

        Event newEvent = eventService.createEvent(event);

        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {

        eventService.deleteEvent(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Integer id, @Valid @RequestBody UpdateEventDto event) {

        eventService.updateEvent(id, event);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/purchaseTicket")
    public ResponseEntity<Ticket> purchaseTicket(@PathVariable Integer id,
            @Valid @RequestBody PurchaseTicketDto purchaseTicketDto) {

        Ticket ticket =  eventService.purchaseTicket(id, purchaseTicketDto);

        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelEvent(@PathVariable Integer id) {

        eventService.cancelEvent(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
