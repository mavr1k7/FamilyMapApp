package com.teranpeterson.client.helpers;

import com.google.gson.Gson;
import com.teranpeterson.client.result.EventResult;
import com.teranpeterson.client.result.LoginResult;
import com.teranpeterson.client.result.PersonResult;

import java.io.Reader;

/**
 * Deserializer for json objects
 *
 * @author Teran Peterson
 * @version v0.1.2
 */
class Deserializer {
    /**
     * Deserialize the login response from server
     *
     * @param reader Reader object
     * @return LoginResult object with user information
     */
    static LoginResult loginResult(Reader reader) { return new Gson().fromJson(reader, LoginResult.class); }

    /**
     * Deserialize the list of people pulled from server
     *
     * @param reader Reader object
     * @return List of people related to the user
     */
    static PersonResult personResult(Reader reader) { return new Gson().fromJson(reader, PersonResult.class); }

    /**
     * Deserialize the list of events pulled from server
     *
     * @param reader Reader object
     * @return List of events related to the user
     */
    static EventResult eventResult(Reader reader) { return new Gson().fromJson(reader, EventResult.class); }
}
