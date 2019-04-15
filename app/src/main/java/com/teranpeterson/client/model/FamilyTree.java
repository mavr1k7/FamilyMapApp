package com.teranpeterson.client.model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FamilyTree {
    private static FamilyTree sFamilyTree;

    private String mRootUserID;
    private String mAuthToken;
    private List<Person> mPersons;
    private List<Event> mEvents;
    private String url;

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
        List<Event> filtered = new ArrayList<>();
        for (Event event : mEvents) {
            if (Filter.get().filter(event)) {

            }
        }
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

    public void findEventTypes() {
        Map<String, Boolean> types = new HashMap<>();

        for (Event event : mEvents) {
            String type = event.getEventType().toLowerCase();
            if (!types.containsKey(type)) {
                types.put(type, Boolean.FALSE);
            }
        }
        Filter.get().setEventTypes(types);
    }

    public String getAuthToken() {
        return mAuthToken;
    }

    public void setAuthToken(String authToken) {
        this.mAuthToken = authToken;
    }

    public void setRootUserID(String userID) { this.mRootUserID = userID; }

    public boolean isLoggedIn() {
        return (mAuthToken != null && !mAuthToken.isEmpty());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void clear() {
        mPersons.clear();
        mEvents.clear();
    }

    public void associate() {
        Person root = getPerson(mRootUserID);
        if (root != null) {
            root.setEvents(getMyEvents(mRootUserID));
            root.setSide("both");
            associateHelper(root.getFather(), "father", mRootUserID);
            associateHelper(root.getMother(), "mother", mRootUserID);
            Log.d("Associate", toString());
        }
    }

    private void associateHelper(String id, String side, String child) {
        Person person = getPerson(id);
        if (person == null) return;

        person.setEvents(getMyEvents(id));
        person.setSide(side);
        person.setChild(child);
        associateHelper(person.getFather(), side, id);
        associateHelper(person.getMother(), side, id);
    }

    public List<Person> searchPersons(String match) {
        match = match.toLowerCase();

        List<Person> results = new ArrayList<>();
        for (Person person : mPersons) {
            if (person.getFirstName().toLowerCase().contains(match) || person.getLastName().toLowerCase().contains(match)) {
                results.add(person);
            }
        }
        return results;
    }

    public List<Event> searchEvents(String match) {
        match = match.toLowerCase();

        List<Event> results = new ArrayList<>();
        for (Event event : getEvents()) {
            if (event.getCountry().toLowerCase().contains(match) || event.getCity().toLowerCase().contains(match)
                    || event.getEventType().toLowerCase().contains(match) || Integer.toString(event.getYear()).toLowerCase().contains(match)) {
                results.add(event);
            }
        }
        return results;
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
