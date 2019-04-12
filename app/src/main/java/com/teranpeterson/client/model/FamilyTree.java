package com.teranpeterson.client.model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FamilyTree {
    private static FamilyTree sFamilyTree;

    private String mRootUserID;
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

    private List<Event> getMyEvents(String id) {
        List<Event> events = new ArrayList<>();
        for (Event event : mEvents) {
            if (event.getPersonID().equals(id))
                events.add(event);
        }
        return events;
    }

    public void setEvents(List<Event> events) {
        this.mEvents = events;
    }

    public void setAuthToken(String authToken) {
        this.mAuthToken = authToken;
    }

    public void setRootUserID(String userID) { this.mRootUserID = userID; }

    public boolean isLoggedIn() {
        return (mAuthToken != null && !mAuthToken.isEmpty());
    }

    public void associate() {
        Person root = getPerson(mRootUserID);
        if (root != null) {
            root.setEvents(getMyEvents(mRootUserID));
            root.setSide("both");
            associateHelper(root.getFather(), "father");
            associateHelper(root.getMother(), "mother");
            Log.d("Associate", toString());
        }
    }

    private void associateHelper(String id, String side) {
        Person person = getPerson(id);
        if (person == null) return;

        person.setEvents(getMyEvents(id));
        person.setSide(side);
        associateHelper(person.getFather(), side);
        associateHelper(person.getMother(), side);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Person person : mPersons) {
            out.append(person.getFirstName()).append(" ").append(person.getLastName()).append(": side - ").append(person.getSide()).append(", events - ");
            for (Event event : person.getEvents()) {
                out.append(event.getEventID()).append(", ");
            }
            out.append("\n");
        }
        return out.toString();
    }

    // Sorted list of events for each person
    // List of children fro each person
    // Event types
    // Event type colors
    // Paternal ancestors
    // Maternal ancestors
}
