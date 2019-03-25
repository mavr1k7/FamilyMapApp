package com.teranpeterson.client.result;

import com.teranpeterson.client.model.Event;

import java.util.List;

/**
 * Contains information about the results of an Event(s) Request
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class EventResult extends Result {
    /**
     * List of all the data to return
     */
    private List<Event> data;
    /**
     * Event to return
     */
    private Event event;

    /**
     * Creates a successful event result with information about ALL the data
     *
     * @param data Stores the data
     */
    public EventResult(List<Event> data) {
        this.data = data;
        super.success = true;
    }

    /**
     * Creates a successful event result with information about the event
     *
     * @param event Stores the event
     */
    public EventResult(Event event) {
        this.event = event;
        super.success = true;
    }

    /**
     * Creates a failing event result with the given error message
     *
     * @param message Description of the error
     */
    public EventResult(String message) {
        super.message = message;
        super.success = false;
    }

    /**
     * Gets a list of all the data related to the user
     *
     * @return List of all the data related to the user
     */
    public List<Event> getData() {
        return data;
    }

    /**
     * Sets a list of all the data related to the user
     *
     * @param data List of all the data related to the user
     */
    public void setData(List<Event> data) {
        this.data = data;
    }

    /**
     * Adds a event to the list of data related to the user
     *
     * @param event Event related to the user
     */
    public void addEvent(Event event) {
        this.data.add(event);
    }

    /**
     * Gets the event related to the user
     *
     * @return Event related to the user
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets the event related to the user
     *
     * @param event Event related to the user
     */
    public void setEvent(Event event) {
        this.event = event;
    }
}
