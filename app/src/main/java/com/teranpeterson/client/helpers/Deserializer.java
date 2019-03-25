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
public class Deserializer {
    public static LoginResult loginResult(Reader reader) { return new Gson().fromJson(reader, LoginResult.class); }

    public static PersonResult personResult(Reader reader) { return new Gson().fromJson(reader, PersonResult.class); }

    public static EventResult eventResult(Reader reader) { return new Gson().fromJson(reader, EventResult.class); }
}
