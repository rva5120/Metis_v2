package com.example.android.metis;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "apps_table")
class Apps {

    // Attributes
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "timestamp")
    private String timestamp;

    @ColumnInfo(name = "apps")
    private String apps;


    // Constructor
    Apps(@NonNull String timestamp, String apps) {
        this.timestamp = timestamp;
        this.apps = apps;
    }


    // Methods
    @NonNull
    String getTimestamp() {
        return timestamp;
    }
    String getApps() {
        return apps;
    }

    void setTimestamp(@NonNull String timestamp) {
        this.timestamp = timestamp;
    }
    void setApps(String apps) {
        this.apps = apps;
    }
}