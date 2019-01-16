package com.example.android.metis;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface InformationDao {

    // Insert a Habit
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHabit(Habit habit);

    // Get all Habits
    @Query("SELECT * FROM habits_table")
    List<Habit> getAllHabits();

    // Insert a List of Installed Apps
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertApps(Apps apps);

    // Get all installed apps recorded
    @Query("SELECT * FROM apps_table")
    List<Apps> getAllApps();

    // Insert a Preferred Article
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPreferredArticle(PreferredArticle preferredArticle);

    // Get all preferred articles
    @Query("SELECT * FROM preferred_articles_table")
    List<PreferredArticle> getAllPreferredArticles();

    // Insert Recognized Activity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecognizedActivity(RecognizedActivity recognizedActivity);

    // Get Recognized Activity
    @Query("SELECT * FROM current_activity_table")
    List<RecognizedActivity> getRecognizedActivity();
}
