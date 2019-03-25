package com.teranpeterson.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client {
    public LoginResult register(String serverHost, String serverPort, RegisterRequest requestData) throws IOException {
        URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        try {
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Authorization", "");
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            OutputStream requestBody = http.getOutputStream();
            OutputStreamWriter sw = new OutputStreamWriter(requestBody);
            sw.write(Serializer.serialize(requestData));
            sw.flush();
            requestBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                Reader reader = new InputStreamReader(respBody);
                return Deserializer.loginResult(reader);
            } else {
                InputStream respBody = http.getErrorStream();
                Reader reader = new InputStreamReader(respBody);
                return Deserializer.loginResult(reader);
            }
        } finally {
            http.disconnect();
        }
    }

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
            if (request.getClass().toString().equals("class com.teranpeterson.client.LoginRequest")) {
                sw.write(Serializer.serialize((LoginRequest) request));
            } else if (request.getClass().toString().equals("class com.teranpeterson.client.RegisterRequest")) {
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
}
