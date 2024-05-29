package com.efrei.olympic_api.exception;

import com.efrei.olympic_api.model.Event;

public class NotEnoughTicketException extends RuntimeException{

    public NotEnoughTicketException(Event event, Integer ticketsLeft) {
        super("Not enough tickets left for event " + event.getName() + ". Only " + ticketsLeft + " tickets left.");
    }
    
}
