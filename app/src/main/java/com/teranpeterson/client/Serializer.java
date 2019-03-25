package com.teranpeterson.client;

import com.google.gson.Gson;

/**
 * Serializer for json objects
 *
 * @author Teran Peterson
 * @version v0.1.2
 */
public class Serializer {
    public static String serialize(LoginResult result) {
        return new Gson().toJson(result);
    }

    public static String serialize(RegisterRequest request) { return new Gson().toJson(request); }
}
