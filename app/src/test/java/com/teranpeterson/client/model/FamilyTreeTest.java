package com.teranpeterson.client.model;

import com.teranpeterson.client.helpers.ServerProxy;
import com.teranpeterson.client.request.LoginRequest;
import com.teranpeterson.client.request.Request;
import com.teranpeterson.client.result.LoginResult;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FamilyTreeTest {

    @Before
    public void setUp() throws Exception {
        Filter.get().reset();
        FamilyTree.get().clear();
        Request loginRequest = new LoginRequest("username", "password");
        String url = "http://localhost:8080";
        LoginResult result = ServerProxy.loginRegister(url + "/user/login", loginRequest);
        ServerProxy.syncPersons(url, result.getAuthToken());
    }

    @Test
    public void associatePass() {
        // Associate all the information in the system
        FamilyTree.get().associate();
        Person person = FamilyTree.get().getPerson("84bae1");

        // Check that a person's data was linked correctly
        assertNotNull(person);
        assertEquals("61b6cc", person.getChild());
        assertEquals("762db8", person.getFather());
        assertEquals("475c20", person.getMother());
        assertEquals("bb63ed", person.getSpouse());
    }

    @Test
    public void associateFail() {
        // Associate all the information in the system
        FamilyTree.get().associate();
        Person person = FamilyTree.get().getPerson("475c20");

        // Check that incorrect data was not linked to a person
        assertNotNull(person);
        assertNull(person.getFather());
        assertNull(person.getMother());
    }

    @Test
    public void sortPass() {
        List<Person> persons = new ArrayList<>();
        List<Event> events = new ArrayList<>();

        // Create a new person
        Person person = new Person("12345", "firstname", "lastname", "m", "23456", "34567", "45678");
        persons.add(person);

        // Create a series of events with crazy years in the wrong order
        Event event1 = new Event("4", "12345", 1.0, -1.0, "country", "city", "death", 1900);
        Event event2 = new Event("1", "12345", 1.0, -1.0, "country", "city", "baptism", 1996);
        Event event3 = new Event("0", "12345", 1.0, -1.0, "country", "city", "birth", 2099);
        Event event4 = new Event("3", "12345", 1.0, -1.0, "country", "city", "marriage", 2018);
        Event event5 = new Event("2", "12345", 1.0, -1.0, "country", "city", "divorce", 2018);
        events.add(event1); // Birth should be first always
        events.add(event2); // Baptism should be next, sorted by year
        events.add(event3); // Divorce third, sorted alphabetically because of matching year
        events.add(event4); // Marriage forth, sorted alphabetically because of matching year
        events.add(event5); // Death should be last always

        FamilyTree.get().setPersons(persons);
        FamilyTree.get().setEvents(events);

        List<Event> myEvents = FamilyTree.get().getMyEvents("12345");

        // Check that the events were sorted correctly
        System.out.println(myEvents.get(0).getEventID());
        System.out.println(myEvents.get(1).getEventID());
        System.out.println(myEvents.get(2).getEventID());
        System.out.println(myEvents.get(3).getEventID());
        System.out.println(myEvents.get(4).getEventID());

        assertEquals("0", myEvents.get(0).getEventID());
        assertEquals("1", myEvents.get(1).getEventID());
        assertEquals("2", myEvents.get(2).getEventID());
        assertEquals("3", myEvents.get(3).getEventID());
        assertEquals("4", myEvents.get(4).getEventID());
    }

    @Test
    public void sortFail() {
        List<Person> persons = new ArrayList<>();
        List<Event> events = new ArrayList<>();

        // Create a new person
        Person person = new Person("12345", "firstname", "lastname", "m", "23456", "34567", "45678");
        persons.add(person);

        // Create two events
        Event event1 = new Event("4", "12345", 1.0, -1.0, "country", "city", "death", 1900);
        Event event2 = new Event("0", "12345", 1.0, -1.0, "country", "city", "birth", 2099);
        events.add(event1); // Birth should be first always
        events.add(event2); // Divorce third, sorted alphabetically because of matching year

        FamilyTree.get().setPersons(persons);
        FamilyTree.get().setEvents(events);

        // Disable filters for the two events
        FamilyTree.get().updateEventEnabled("birth", false);
        FamilyTree.get().updateEventEnabled("death", false);

        List<Event> myEvents = FamilyTree.get().getMyEvents("12345");

        // Check that no events were returned, filtered.
        assertTrue(myEvents.isEmpty());
    }

    @Test
    public void searchPass() {
        // Search for a person by name
        List<Person> persons = FamilyTree.get().searchPersons("vivan");
        assertNotNull(persons);
        assertEquals("42709f", persons.get(0).getPersonID());

        // Search for an event by location
        List<Event> events = FamilyTree.get().searchEvents("milan");
        assertNotNull(events);
        assertEquals("882030", events.get(0).getEventID());
    }

    @Test
    public void searchFail() {
        // Search for a person by name
        List<Person> persons = FamilyTree.get().searchPersons("notperson");
        assertTrue(persons.isEmpty());

        // Search for an event by location
        List<Event> events = FamilyTree.get().searchEvents("notevent");
        assertTrue(events.isEmpty());
    }
}