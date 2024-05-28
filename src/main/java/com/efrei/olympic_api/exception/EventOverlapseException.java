package com.efrei.olympic_api.exception;

import com.efrei.olympic_api.model.Event;

public class EventOverlapseException extends RuntimeException {

    public EventOverlapseException(Event existingEvent, Event newEvent) {
        super("Event " + newEvent.getName() + " overlaps with event " + existingEvent.getName() + " at stadium "
                + existingEvent.getStadium().getName() + " on " + existingEvent.getDateTime() + " for "
                + existingEvent.getDurationInMinute() + " minutes");
    }

}
