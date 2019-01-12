package com.example.android.metis;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

@Dao
public interface InformationDao {

    // Insert a Habit
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHabit(Habit habit);

    // Insert a List of Installed Apps
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertApps(Apps apps);

    // Insert a Preferred Article
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPreferredArticle(PreferredArticle preferredArticle);
}
