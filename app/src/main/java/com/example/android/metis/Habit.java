package com.example.android.metis;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "habits_table")
class Habit {

    // Attributes
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "timestamp")
    private String timestamp;

    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "state")
    private String state;

    @ColumnInfo(name = "onWiFi")
    private String onWiFi;

    @ColumnInfo(name = "organisation")
    private String organisation;

    @ColumnInfo(name = "carrier")
    private String carrier;

    @ColumnInfo(name = "batteryState")
    private String batteryState;

    @ColumnInfo(name = "currentActivity")
    private String currentActivity;

    @ColumnInfo(name = "activityConfidence")
    private String activityConfidence;

    @ColumnInfo(name = "activityTimestamp")
    private String activityTimestamp;

    @ColumnInfo(name = "appState")
    private String appState;


    // Constructor
    Habit(@NonNull String timestamp, String id, String city, String state,
          String onWiFi, String organisation, String carrier,
          String batteryState, String currentActivity,
          String activityConfidence, String activityTimestamp,
          String appState) {
        this.timestamp = timestamp;
        this.id = id;
        this.city = city;
        this.state = state;
        this.onWiFi = onWiFi;
        this.organisation = organisation;
        this.carrier = carrier;
        this.batteryState = batteryState;
        this.currentActivity = currentActivity;
        this.activityConfidence = activityConfidence;
        this.activityTimestamp = activityTimestamp;
        this.appState = appState;
    }


    // Methods
    @NonNull
    String getTimestamp() {
        return timestamp;
    }
    String getid() {
        return id;
    }
    String getCity() {
        return city;
    }
    String getstate() {
        return state;
    }
    String getOnWiFi() {
        return onWiFi;
    }
    String getOrganisation() {
        return organisation;
    }
    String getCarrier() {
        return carrier;
    }
    String getBatteryState() {
        return batteryState;
    }
    String getCurrentActivity() {
        return currentActivity;
    }
    String getActivityConfidence() {
        return activityConfidence;
    }
    String getActivityTimestamp() {
        return activityTimestamp;
    }
    String getAppState() {
        return appState;
    }

    void setTimestamp(@NonNull String timestamp) {
        this.timestamp = timestamp;
    }

    void setid(String id) {
        this.id = id;
    }

    void setCity(String city) {
        this.city = city;
    }

    void setstate(String state) {
        this.state = state;
    }

    void setOnWiFi(String onWiFi) {
        this.onWiFi = onWiFi;
    }

    void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    void setBatteryState(String batteryState) {
        this.batteryState = batteryState;
    }

    void setCurrentActivity(String currentActivity) {
        this.currentActivity = currentActivity;
    }

    void setActivityConfidence(String activityConfidence) {
        this.activityConfidence = activityConfidence;
    }

    void setActivityTimestamp(String activityTimestamp) {
        this.activityTimestamp = activityTimestamp;
    }

    void setAppState(String appState) {
        this.appState = appState;
    }
}
