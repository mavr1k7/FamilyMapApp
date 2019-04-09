package com.teranpeterson.client.model;

import java.util.ArrayList;
import java.util.List;

public class FamilyTree {
    private static FamilyTree sFamilyTree;

    private String mAuthToken;
    private List<Person> mPersons;
    private List<Event> mEvents;

    private FamilyTree() {
        mPersons = new ArrayList<>();
        mEvents = new ArrayList<>();
    }

    public static FamilyTree get() {
        if (sFamilyTree == null) {
            sFamilyTree = new FamilyTree();
        }
        return sFamilyTree;
    }

//    public List<Person> getPersons() {
//        return mPersons;
//    }

    public Person getPerson(String id) {
        for (Person person : mPersons) {
            if (person.getPersonID().equals(id)) {
                return person;
            }
        }
        return null;
    }

    public void setPersons(List<Person> persons) {
        this.mPersons = persons;
    }

    public List<Event> getEvents() {
        return mEvents;
    }

    public Event getEvent(String id) {
        for (Event event : mEvents) {
            if (event.getEventID().equals(id)) {
                return event;
            }
        }
        return null;
    }

    public void setEvents(List<Event> events) {
        this.mEvents = events;
    }

//    public String getAuthToken() {
//        return mAuthToken;
//    }

    public void setAuthToken(String authToken) {
        this.mAuthToken = authToken;
    }

    public boolean isLoggedIn() {
        return (mAuthToken != null && !mAuthToken.isEmpty());
    }

    // Sorted list of events for each person
    // List of children fro each person
    // Event types
    // Event type colors
    // Paternal ancestors
    // Maternal ancestors
}
