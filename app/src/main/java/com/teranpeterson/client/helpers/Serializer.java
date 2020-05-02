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
    /**
     * Serialize register request before sending it to server
     *
     * @param request Request object with the needed information
     * @return JSon object with LoginResult
     */
    static String serialize(RegisterRequest request) { return new Gson().toJson(request); }

    /**
     * Serialize login request before sending it to server
     *
     * @param request Login object with the needed information
     * @return JSon object with LoginResult
     */
    static String serialize(LoginRequest request) { return new Gson().toJson(request); }
}
