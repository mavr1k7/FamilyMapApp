package com.teranpeterson.client.model;

import com.teranpeterson.client.helpers.ServerProxy;
import com.teranpeterson.client.request.LoginRequest;
import com.teranpeterson.client.request.Request;
import com.teranpeterson.client.result.LoginResult;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FilterTest {

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
    public void filterPass() {
        // Set filters
        Filter.get().setFemale(false);
        Filter.get().setMother(false);
        FamilyTree.get().updateEventEnabled("birth", false);

        Event event1 = new Event("12345", "84bae1", 1.0, -1.0, "Country", "City", "Birth", 1996);
        Event event2 = new Event("12345", "ce0507", 1.0, -1.0, "Country", "City", "Baptism", 2000);
        Event event3 = new Event("12345", "61b6cc", 1.0, -1.0, "Country", "City", "Marriage", 2010);
        Event event4 = new Event("12345", "6ce430", 1.0, -1.0, "Country", "City", "Death", 2019);

        // Filter by type, birth is disabled
        assertFalse(Filter.get().filter(event1));

        // Filter by gender, female is disabled
        assertFalse(Filter.get().filter(event2));

        // Filter by side, mother's side is disabled
        assertFalse(Filter.get().filter(event3));

        // Pass all the filters
        assertTrue(Filter.get().filter(event4));
    }

    @Test
    public void filterFail() {
        // Set filters
        Filter.get().setMother(false);
        Filter.get().setFather(false);
        Event event = new Event("12345", "f68935", 1.0, -1.0, "Country", "City", "Birth", 1996);

        // Filter by side, both sides are disabled, but the root person has no side
        assertTrue(Filter.get().filter(event));
    }
}