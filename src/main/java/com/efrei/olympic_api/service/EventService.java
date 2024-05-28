package com.efrei.olympic_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efrei.olympic_api.dto.CreateEventDto;
import com.efrei.olympic_api.dto.UpdateEventDto;
import com.efrei.olympic_api.enums.EntityEnum;
import com.efrei.olympic_api.exception.RessourceNotFoundException;
import com.efrei.olympic_api.model.Event;
import com.efrei.olympic_api.model.Stadium;
import com.efrei.olympic_api.repository.EventRepository;
import com.efrei.olympic_api.repository.StadiumRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private StadiumRepository stadiumRepository;

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
        updatedEvent.setIsActive(event.getIsActive());
        updatedEvent.setStadium(stadium.get());

        eventRepository.save(updatedEvent);

        return true;
    }

}
