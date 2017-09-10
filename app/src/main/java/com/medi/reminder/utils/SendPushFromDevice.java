package com.medi.reminder.utils;

import android.provider.SyncStateContract;
import android.util.Log;

import com.medi.reminder.Constants;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SendPushFromDevice {

    private final String USER_AGENT = "Mozilla/5.0";

    // HTTP POST request
    public String sendPush(String deviceToken) throws Exception {
        String authKey = Constants.SERVER_KEY; // You FCM AUTH key

        String url = "https://fcm.googleapis.com/fcm/send";
        URL obj = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json");

        //notification object
        JSONObject notification = new JSONObject();
        notification.put("title", "FCM Notificatoin Title"); // Notification title
        notification.put("text", "Hello First Test notification"); // Notification body

        //main object
        JSONObject data = new JSONObject();
        //add notificaiton body
        data.put("notification", notification);
        //add receipeint
        data.put("to", deviceToken);
        JSONObject info = new JSONObject();
        info.put("title", "FCM Notificatoin Title"); // Notification title
        info.put("body", "Hello First Test notification"); // Notification body
        data.put("data", info);


        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data.toString());
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        Log.e("code", "Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return (response.toString());

    }

}