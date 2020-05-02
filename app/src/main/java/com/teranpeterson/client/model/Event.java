package com.teranpeterson.client.model;

/**
 * Events store information about significant events from a persons life, ie. birth, marriage, death, etc. Each event
 * has a personID that corresponds to a person in the system. The latitude and longitude values are used by the client
 * to map these events. Simplified version of the model found in the server.
 *
 * @author Teran Peterson
 * @version v0.1.2
 */
public class Event {
    /**
     * Unique id for the event
     */
    private String eventID;
    /**
     * Unique id of the person the event happened to
     */
    private String personID;
    /**
     * Latitude where the event took place
     */
    private double latitude;
    /**
     * Longitude where the event took place
     */
    private double longitude;
    /**
     * Country where the event took place
     */
    private String country;
    /**
     * City where the event took place
     */
    private String city;
    /**
     * Type of event (birth, baptism, christening, marriage, death, or burial)
     */
    private String eventType;
    /**
     * Year the event took place
     */
    private int year;

    /**
     * Creates an event with the given data
     *
     * @param eventID    Event's id
     * @param personID   ID of the person the event happened to
     * @param latitude   Latitude where the event took place
     * @param longitude  Longitude where the event took place
     * @param country    Country where the event took place
     * @param city       City where the event took place
     * @param eventType       Type of event (birth, baptism, christening, marriage, death, or burial)
     * @param year       Year the event took place
     */
    public Event(String eventID, String personID, double latitude, double longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Gets the eventID
     *
     * @return The event's ID
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * Gets the personID of the person this even happened to
     *
     * @return A personID
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * Gets the latitude where the event took place
     *
     * @return A floating point number for latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets the longitude where the event took place
     *
     * @return A floating point number for longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Gets the country where the event took place
     *
     * @return Country name where the event took place
     */
    public String getCountry() {
        return country;
    }

    /**
     * Gets the city where the event took place
     *
     * @return City where the event took place
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets the ype of event
     *
     * @return birth, baptism, christening, marriage, death, or burial
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Gets the year the event took place
     *
     * @return Year the event took place
     */
    public int getYear() {
        return year;
    }
}
