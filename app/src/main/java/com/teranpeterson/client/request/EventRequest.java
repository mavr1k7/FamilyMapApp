package com.teranpeterson.client.request;

/**
 * Contains information about the Event(s) Request
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class EventRequest {
    /**
     * ID of the event to load
     */
    private String eventID;
    /**
     * Authentication token of the current user (used to find related persons)
     */
    private String authToken;

    /**
     * Creates an event request for ALL events for ALL persons related to the current user
     *
     * @param authToken Authentication token of the current user
     */
    public EventRequest(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Creates an event request for the given eventID
     *
     * @param eventID   ID of the event to load
     * @param authToken Authentication token of the current user
     */
    public EventRequest(String eventID, String authToken) {
        this.eventID = eventID;
        this.authToken = authToken;
    }

    /**
     * Gets the ID of the event to load
     *
     * @return ID of the event to load
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * Sets the ID of the event to load
     *
     * @param eventID ID of the event to load
     */
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    /**
     * Gets the user's authentication token
     *
     * @return User's authentication token
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets the user's authentication token
     *
     * @param authToken User's authentication token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
