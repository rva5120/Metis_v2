package com.example.android.metis;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

@Entity(tableName = "preferred_articles_table")
class PreferredArticle {

    // Attributes
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "timestamp")
    private String timestamp;

    @ColumnInfo(name = "article")
    private String article;


    // Constructor
    PreferredArticle(@NonNull String timestamp, String article) {
        this.timestamp = timestamp;
        this.article = article;
    }


    // Methods
    @NonNull
    String getTimestamp() {
        return timestamp;
    }
    String getApps() {
        return article;
    }

    void setTimestamp(@NonNull String timestamp) {
        this.timestamp = timestamp;
    }
    void setApps(String apps) {
        this.article = apps;
    }
}
