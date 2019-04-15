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

public class ServerProxy {
    public static LoginResult login(String uri, Request request) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        try {
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Authorization", "");
            http.addRequestProperty("Accept", "application/json");
            http.connect();

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
            FamilyTree.get().setPersons(result.getData());
            syncEvents(uri, authToken);
            return result;
        } finally {
            http.disconnect();
        }
    }

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
