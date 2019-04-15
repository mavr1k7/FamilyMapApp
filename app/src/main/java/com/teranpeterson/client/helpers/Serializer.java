package com.teranpeterson.client.helpers;

import com.google.gson.Gson;
import com.teranpeterson.client.request.LoginRequest;
import com.teranpeterson.client.request.RegisterRequest;

/**
 * Serializer for json objects
 *
 * @author Teran Peterson
 * @version v0.1.2
 */
class Serializer {
    static String serialize(RegisterRequest request) { return new Gson().toJson(request); }

    static String serialize(LoginRequest request) { return new Gson().toJson(request); }
}
