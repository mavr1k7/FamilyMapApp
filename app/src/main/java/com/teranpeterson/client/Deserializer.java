package com.teranpeterson.client;

import com.google.gson.Gson;
import java.io.Reader;

/**
 * Deserializer for json objects
 *
 * @author Teran Peterson
 * @version v0.1.2
 */
public class Deserializer {
    public static RegisterRequest registerRequest(Reader reader) { return new Gson().fromJson(reader, RegisterRequest.class); }

    public static LoginResult loginResult(Reader reader) { return new Gson().fromJson(reader, LoginResult.class); }
}
