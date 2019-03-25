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
    public static void login(String serverHost, String serverPort) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Authorization", "");
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            RegisterRequest gsonRequest = new RegisterRequest("username", "password", "email", "firstname", "lastname", "m");

            OutputStream requestBody = http.getOutputStream();
            OutputStreamWriter sw = new OutputStreamWriter(requestBody);
            sw.write(Serializer.serialize(gsonRequest));
            sw.flush();
            requestBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                Reader reader = new InputStreamReader(respBody);
                LoginResult result = Deserializer.loginResult(reader);
                System.out.println(result.getAuthToken());
            } else {
                InputStream respBody = http.getErrorStream();
                Reader reader = new InputStreamReader(respBody);
                LoginResult result = Deserializer.loginResult(reader);
                System.out.println(result.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
