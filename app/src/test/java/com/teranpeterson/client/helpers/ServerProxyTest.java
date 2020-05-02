package com.teranpeterson.client.helpers;

import com.teranpeterson.client.model.FamilyTree;
import com.teranpeterson.client.model.Person;
import com.teranpeterson.client.request.LoginRequest;
import com.teranpeterson.client.request.RegisterRequest;
import com.teranpeterson.client.request.Request;
import com.teranpeterson.client.result.LoginResult;
import com.teranpeterson.client.result.PersonResult;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServerProxyTest {
    private String url;
    private String authtoken;
    private Request loginRequest;
    private Request badLoginRequest;
    private Request registerRequest;
    private Request badRegisterRequest;

    @Before
    public void setUp() {
        loginRequest = new LoginRequest("username", "password");
        badLoginRequest = new LoginRequest("badusername", "badpassword");
        registerRequest = new RegisterRequest("oldusername", "oldpassword", "email", "firstname", "lastname", "m");
        badRegisterRequest = new RegisterRequest("notusername", "notpassword", "email", "firstname", "lastname", "m");
        authtoken = "6fb8759b-d238-4f1c-a8ca-b995aaefc794";
        url = "http://localhost:8080";
    }

    @Test
    public void loginPass() throws Exception {
        // Login the user
        LoginResult result = ServerProxy.loginRegister(url + "/user/login", loginRequest);

        // Check that it was successful and that a valid auth token was returned
        assertTrue(result.isSuccess());
        assertNotNull(result.getAuthToken());
        assertNotNull(result.getPersonID());
    }

    @Test
    public void loginFail() throws Exception {
        // Attempt to login a new user
        LoginResult result = ServerProxy.loginRegister(url + "/user/login", badLoginRequest);

        // Check that it was not successful and that an error message was returned
        assertFalse(result.isSuccess());
        assertNotNull(result.getMessage());
        assertEquals("ERROR: Invalid userName or password", result.getMessage());
    }

    @Test
    public void registerPass() throws Exception {
        // Register a new user
        LoginResult result = ServerProxy.loginRegister(url + "/user/register", registerRequest);

        // Check that it was successful and that a valid auth token was returned
        assertTrue(result.isSuccess());
        assertNotNull(result.getAuthToken());
        assertNotNull(result.getPersonID());
    }

    @Test
    public void registerFail() throws Exception {
        // Attempt to register a new user
        LoginResult result = ServerProxy.loginRegister(url + "/user/register", badRegisterRequest);

        // Check that it was not successful and that an error message was returned
        assertFalse(result.isSuccess());
        assertNotNull(result.getMessage());
        assertEquals("ERROR: Username is already in use", result.getMessage());
    }

    @Test
    public void syncPass() throws Exception {
        // Load data with a static auth token
        PersonResult result = ServerProxy.syncPersons(url, authtoken);

        // Check that the correct data was loaded
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());
        assertNotNull(result.getData());
        Person person = FamilyTree.get().getPerson("1a9b8d");
        assertNotNull(person);
        assertEquals("0101fa", person.getFather());
    }

    @Test
    public void syncFail() throws Exception {
        // Load data with a fake auth token
        PersonResult result = ServerProxy.syncPersons(url, "FakeAuthToken");

        // Check that the correct data was loaded
        assertFalse(result.isSuccess());
        assertNotNull(result.getMessage());
        assertEquals("ERROR: Invalid auth token", result.getMessage());
        assertNull(result.getData());
        Person person = FamilyTree.get().getPerson("1a9b8d");
        assertNull(person);
    }
}