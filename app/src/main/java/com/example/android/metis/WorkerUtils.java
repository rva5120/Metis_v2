package com.example.android.metis;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

final class WorkerUtils {

    static Habit getCurrentHabit(Context context, Boolean appState, String activity) {

        Habit habit;

        try {
            // Get Public IP JSON Object
            StringBuilder result = getPublicIPResult();
            JSONObject json = new JSONObject(result.toString());

            // Get Timestamp
            String timestamp = json.getString("timezone");

            // Get ID
            String id = "0";

            // Get City
            String city = json.getString("city");

            // Get State
            String state = json.getString("state");

            // Get OnWiFi
            String onWiFi = json.getString("carrier");
            if (onWiFi != null) {
                // If there is a "carrier" field, then we are on cellular not WiFi
                onWiFi = "False";
            } else {
                onWiFi = "True";
            }

            // Get Organisation;
            String organisation = json.getString("organisation");

            // Get Carrier
            String carrier = json.getString("carrier");

            // Get Battery State
            String batteryState = getBatteryState(context);

            // Get Current Activity
            String currentActivity = activity;

            // Get App State
            String appStateString;
            if (appState == Boolean.TRUE) {
                appStateString = "USING_APP";
            } else {
                appStateString = "CLOSED_APP";
            }


            // Construct Habit
            habit = new Habit(timestamp, id, city, state, onWiFi,
                    organisation, carrier, batteryState, currentActivity, appStateString);

        } catch (JSONException j) {
            j.printStackTrace();
            habit = null;
        }

        return habit;

    }

    static StringBuilder getPublicIPResult() {

        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL("https://api.ipdata.co/?api-key=test");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        } catch (MalformedURLException m) {
            m.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }

        return stringBuilder;
    }

    static String getBatteryState(Context context) {

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, intentFilter);

        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        int chargeType = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

        if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
            if (chargeType == BatteryManager.BATTERY_PLUGGED_AC) {
                return "AC";
            } else if (chargeType == BatteryManager.BATTERY_PLUGGED_USB) {
                return "USB";
            } else if (chargeType == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
                return "WIRELESS";
            } else {
                return "UNKNOWN_CODE";
            }
        } else {
            return "NOT_CHARGING";
        }
    }
}
