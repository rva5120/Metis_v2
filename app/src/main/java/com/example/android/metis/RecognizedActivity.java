package com.example.android.metis;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "current_activity_table")
class RecognizedActivity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name  = "id")
    private String id;

    @ColumnInfo(name = "recognizedActivity")
    private String recognizedActivity;

    @ColumnInfo(name = "confidence")
    private String confidence;

    @ColumnInfo(name = "timestamp")
    private String timestamp;


    RecognizedActivity(String recognizedActivity, String confidence, @NonNull String timestamp) {
        id = timestamp;
        this.recognizedActivity = recognizedActivity;
        this.confidence = confidence;
        this.timestamp = timestamp;
    }

    @NonNull String getId() { return id; }
    String getRecognizedActivity() {
        return recognizedActivity;
    }
    String getConfidence() {
        return confidence;
    }
    String getTimestamp() {
        return timestamp;
    }

    void setId(@NonNull String id) { this.id = id; }
    void setRecognizedActivity(String recognizedActivity) {
        this.recognizedActivity = recognizedActivity;
    }
    void setConfidence(String confidence) {
        this.confidence = confidence;
    }
    void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
