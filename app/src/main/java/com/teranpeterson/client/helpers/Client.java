package com.teranpeterson.client.helpers;

import com.teranpeterson.client.result.EventResult;
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

public class Client {
    public LoginResult login(String uri, Request request) throws IOException {
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
            return Deserializer.loginResult(reader);
        } finally {
            http.disconnect();
        }
    }

    public PersonResult syncPersons(String uri, String authToken) throws IOException {
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
            return Deserializer.personResult(reader);
        } finally {
            http.disconnect();
        }
    }

    public EventResult syncEvents(String uri, String authToken) throws IOException {
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
            return Deserializer.eventResult(reader);
        } finally {
            http.disconnect();
        }
    }
}
