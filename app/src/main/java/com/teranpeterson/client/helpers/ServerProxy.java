package com.teranpeterson.client.helpers;

import com.teranpeterson.client.model.FamilyTree;
import com.teranpeterson.client.request.LoginRequest;
import com.teranpeterson.client.result.LoginResult;
import com.teranpeterson.client.result.PersonResult;
import com.teranpeterson.client.request.RegisterRequest;
import com.teranpeterson.client.request.Request;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ServerProxy establishes a connection to the FamilyMapServer and is used to login, register, and sync
 * information between the two. For more information about the http requests, see the documentation for
 * FamilyMapServer. This application currently only uses 3 requests: /user/login, /user/register, /person,
 * and /event.
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class ServerProxy {
    /**
     * Receives a Request object, either Login or Register, and passes it to the server. If successful,
     * the server responds with the user's information and an auth token. Otherwise an error message
     * is returned. Uses the /user/login and the /user/register api.
     *
     * @param uri URL to connect to, entered by the user in the login fragment
     * @param request Request object made from users credentials entered in the login fragment
     * @return Request object with an auth token or an error message
     * @throws IOException Connection error
     */
    public static LoginResult loginRegister(String uri, Request request) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        try {
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Authorization", "");
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            // Converts the request object into either a Login Request or a Register Request.
            OutputStream requestBody = http.getOutputStream();
            OutputStreamWriter sw = new OutputStreamWriter(requestBody);
            if (request.getClass().toString().equals("class com.teranpeterson.client.request.LoginRequest")) {
                sw.write(Serializer.serialize((LoginRequest) request));
            } else if (request.getClass().toString().equals("class com.teranpeterson.client.request.RegisterRequest")) {
                sw.write(Serializer.serialize((RegisterRequest) request));
            }
            sw.flush();
            requestBody.close();

            InputStream respBody;
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                respBody = http.getInputStream();
            } else {
                respBody = http.getErrorStream();
            }

            Reader reader = new InputStreamReader(respBody);
            LoginResult result = Deserializer.loginResult(reader);
            FamilyTree.get().setRootUserID(result.getPersonID());
            return result;
        } finally {
            http.disconnect();
        }
    }

    /**
     * Connects to the server and uses the user's auth token to pull all of the generated user's family
     * information. After all the user's family members have been loaded, it pulls in all of the events.
     * Uses the /person api. Saves persons in the FamilyTree Singleton.
     *
     * @param uri URL to connect to, entered by the user in the login fragment
     * @param authToken User's auth token from the login result
     * @return Object with the user's information or an error message
     * @throws IOException Connection error
     */
    public static PersonResult syncPersons(String uri, String authToken) throws IOException {
        URL url = new URL(uri + "/person");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        try {
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            InputStream respBody;
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                respBody = http.getInputStream();
            } else {
                respBody = http.getErrorStream();
            }

            Reader reader = new InputStreamReader(respBody);
            PersonResult result = Deserializer.personResult(reader);
            if (result.isSuccess()) {
                FamilyTree.get().setPersons(result.getData());
                syncEvents(uri, authToken);
            }
            return result;
        } finally {
            http.disconnect();
        }
    }

    /**
     * Connects to the server and uses the user's auth token to pull all events related to the user's
     * family members. Saves events in the FamilyTree singleton. Also calls the associate() and
     * findEventTypes() methods to connect all the data. Uses the /event api.
     *
     * @param uri URL to connect to
     * @param authToken User's auth token from the login result
     * @throws IOException Connection error
     */
    private static void syncEvents(String uri, String authToken) throws IOException {
        URL url = new URL(uri + "/event");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        try {
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            InputStream respBody;
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                respBody = http.getInputStream();
            } else {
                respBody = http.getErrorStream();
            }

            Reader reader = new InputStreamReader(respBody);
            FamilyTree.get().setEvents(Deserializer.eventResult(reader).getData());
            FamilyTree.get().associate();
            FamilyTree.get().findEventTypes();
        } finally {
            http.disconnect();
        }
    }
}
