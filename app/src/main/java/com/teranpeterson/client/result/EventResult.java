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
