package com.teranpeterson.client.model;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FamilyTree is a singleton that acts as the main source of storage for the user's family data. It
 * contains a list of persons and events pulled from the database. It also stored the user's credentials.
 * The singleton generates a list of event types with associated colors.
 *
 * @author Teran Peterson
 * @version v0.1.2
 */
public class FamilyTree {
    /**
     * Singleton object
     */
    private static FamilyTree sFamilyTree;
    /**
     * Stores the personID of the user that logged in
     */
    private String mRootUserID;
    /**
     * Stores the user's auth token after a successful login
     */
    private String mAuthToken;
    /**
     * Stores a list of the user's family members
     */
    private List<Person> mPersons;
    /**
     * Stores a list of all the events related to the user's family members
     */
    private List<Event> mEvents;
    /**
     * Base URL of the server, entered by the user in the login fragment
     */
    private String mUrl;
    /**
     * Map of all the event types pulled from the server. The key is the event name (lowercase). The
     * corresponding boolean value is whether or not it is enabled by the filters (default TRUE).
     */
    private Map<String, Boolean> mEventTypes;
    /**
     * Map of all the event type colors pulled from the server. THe key is the event name (lowercase).
     * The corresponding Float value is the color for markers of that type (0-360). Based on
     * BitmapDescriptorFactory colors.
     */
    private Map<String, Float> mEventColors;

    /**
     * Constructor for singleton. Private so that access must be through the get() method.
     */
    private FamilyTree() {
        mPersons = new ArrayList<>();
        mEvents = new ArrayList<>();
        mEventTypes = new HashMap<>();
        mEventColors = new HashMap<>();
    }

    /**
     * Returns the FamilyTree singleton object with all of the loaded data
     *
     * @return FamilyTree singleton
     */
    public static FamilyTree get() {
        if (sFamilyTree == null) {
            sFamilyTree = new FamilyTree();
        }
        return sFamilyTree;
    }

    /**
     * Returns the user's auth token. Used to sync events and persons from the server.
     *
     * @return User's auth token
     */
    public String getAuthToken() {
        return mAuthToken;
    }

    /**
     * Sets the user's auth token after successful login/registration.
     *
     * @param authToken User's auth token
     */
    public void setAuthToken(String authToken) {
        this.mAuthToken = authToken;
    }

    /**
     * Sets the user's personID. Used to split person's by father's and mother's side.
     *
     * @param userID User's personID
     */
    public void setRootUserID(String userID) { this.mRootUserID = userID; }

    /**
     * Checks if a valid auth token is stored. Used by the MainActivity to display either the Login
     * or Map fragments.
     *
     * @return True if the user is logged in. Otherwise false.
     */
    public boolean isLoggedIn() {
        return (mAuthToken != null && !mAuthToken.isEmpty());
    }

    /**
     * Returns the URL used to connect to the server. Entered by the user in the login fragment.
     *
     * @return Server's base URL
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Sets the URL used to connect to the server. Entered by the user in the login fragment.
     *
     * @param url Server's base URL
     */
    public void setUrl(String url) {
        this.mUrl = url;
    }

    /**
     * Searches through the list of persons and returns the person object that corresponds with the
     * provided personID.
     *
     * @param id ID of the person to return
     * @return Person with the matching ID
     */
    public Person getPerson(String id) {
        for (Person person : mPersons) {
            if (person.getPersonID().equals(id)) {
                return person;
            }
        }
        return null;
    }

    /**
     * Sets the list of persons loaded from the server.
     *
     * @param persons List of person objects
     */
    public void setPersons(List<Person> persons) {
        this.mPersons = persons;
    }

    /**
     * Sets the list of events loaded from the server.
     *
     * @param events List of event objects
     */
    public void setEvents(List<Event> events) {
        this.mEvents = events;
    }

    /**
     * Returns all the events in the singleton that match the current filters. Used whenever events are
     * needed to ensure that filters are global.
     *
     * @return List of events (filtered)
     */
    public List<Event> getEvents() {
        List<Event> filtered = new ArrayList<>();
        for (Event event : mEvents) {
            if (Filter.get().filter(event)) {
                filtered.add(event);
            }
        }
        return filtered;
    }

    /**
     * Searches through the list of events and returns the event object that corresponds with
     * the provided eventID.
     *
     * @param id ID of the event to return
     * @return Event with the matching ID
     */
    public Event getEvent(String id) {
        for (Event event : mEvents) {
            if (event.getEventID().equals(id)) {
                return event;
            }
        }
        return null;
    }

    /**
     * Returns all the events that correspond to a given Person. Uses the getEvents() method to ensure
     * that the list is filtered. Uses a custom sort method. Birth events are always first, Death events
     * are always last. Everything else is filtered by year and then alphabetically.
     *
     * @param id ID of the person to find events for
     * @return Sorted list of events related to the given person
     */
    public List<Event> getMyEvents(String id) {
        List<Event> events = new ArrayList<>();
        for (Event event : getEvents()) {
            if (event.getPersonID().equals(id))
                events.add(event);
        }
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                if (o1.getEventType().toLowerCase().equals("birth")) return -1; // Birth events always
                if (o2.getEventType().toLowerCase().equals("birth")) return 1;  // come first.

                if (o1.getEventType().toLowerCase().equals("death")) return 1;  // Death events always
                if (o2.getEventType().toLowerCase().equals("death")) return -1; // come last.

                if (o1.getYear() < o2.getYear()) return -1;                     // Sort by year, earliest
                else if (o1.getYear() > o2.getYear()) return 1;                 // first.

                else return o1.getEventType().compareToIgnoreCase(o2.getEventType()); // Then alphabetically
            }
        });
        return events;
    }

    /**
     * Iterates through all of the events loaded from the server. Each new type (normalized to lowercase)
     * is added to the mEventTypes and mEventColors map. The mEventType value is set to TRUE by default
     * and the mEventColor value is set to a new color. Color is based off the BitmapDescriptorFactory
     * colors (0-360). Starts with 120 (Green) and moves upwards. If the value passes 360, it resets to
     * 45. This acts like a random color generator for 9 different colors.
     *
     *      map     =           key          ->              value
     * mEventTypes  = event type (lowercase) -> filter option (enabled/disabled)
     * mEventColors = event type (lowercase) -> color (float 0.0f - 360.0f)
     */
    public void findEventTypes() {
        float hue = 120.0f;
        for (Event event : mEvents) {
            String type = event.getEventType().toLowerCase();
            if (!mEventTypes.containsKey(type)) {
                mEventTypes.put(type, Boolean.TRUE);
                mEventColors.put(type, hue);
                hue += 60.0f;
            }
            if (hue >= 360.0f) hue = 45.0f;
        }
    }

    /**
     * Returns the mapping of event types to their filter options (enabled/disabled)
     *
     * @return Map of event types to boolean values
     */
    public Map<String, Boolean> getEventTypes() {
        return mEventTypes;
    }

    /**
     * Changes an event types filter option from enabled to disabled or vice versa
     *
     * @param type Event type (lowercase)
     * @param enabled True (enabled) or False (disabled)
     */
    public void updateEventEnabled(String type, boolean enabled) {
        mEventTypes.put(type.toLowerCase(), enabled);
    }

    /**
     * Returns the float value corresponding to the event's color. Used to set the color for map markers.
     *
     * @param type Event type (lowercase)
     * @return Float value for color
     */
    public float getEventColor(String type) {
        type = type.toLowerCase();
        if (mEventColors.containsKey(type)) {
            Float color = mEventColors.get(type);
            if (color != null) return color;
        }
        return BitmapDescriptorFactory.HUE_AZURE; // Random default value to return
    }

    /**
     * Recursively iterates through all of the person's in the singleton and links them to make some
     * of the UI login simpler. Starts with the root user and calls the recursive associateHelper()
     * function. Each person is given a side (father or mother), ie which side of the family they are
     * on based on the root user. They are also given the ID of their child.
     */
    public void associate() {
        Person root = getPerson(mRootUserID);
        if (root != null) {
            root.setSide("both");
            associateHelper(root.getFather(), "father", mRootUserID);
            associateHelper(root.getMother(), "mother", mRootUserID);
        }
    }

    /**
     * Recursively sets which side of the family a person is on and sets their child's ID
     *
     * @param id ID of the person to associate
     * @param side Which side of the family they are on (father or mother)
     * @param child ID of the person's child
     */
    private void associateHelper(String id, String side, String child) {
        Person person = getPerson(id);
        if (person == null) return;

        person.setSide(side);
        person.setChild(child);
        associateHelper(person.getFather(), side, id);
        associateHelper(person.getMother(), side, id);
    }

    /**
     * Searches through all of the persons in the singleton and returns a list of all the persons that
     * match the given criteria. Persons are match on by their first and last names (normalized to
     * lowercase).
     *
     * @param match Criteria to match on
     * @return List of persons that match the given criteria
     */
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

    /**
     * Searches through of of the events in the singleton and returns a list of all the events that
     * match the given criteria. Events are match on by their country, city, event type, and year
     * (normalized to lowercase).
     *
     * @param match Criteria to match on
     * @return List of events that match the given criteria
     */
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

    /**
     * Clears all of the persons and events from the singleton.
     */
    public void clear() {
        mPersons.clear();
        mEvents.clear();
    }
}
