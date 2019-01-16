package com.example.android.metis;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;


@Database(entities = {Habit.class, Apps.class, PreferredArticle.class, RecognizedActivity.class}, version = 1, exportSchema = false)
abstract class InformationDatabase extends RoomDatabase {

    // Attributes
    private static InformationDatabase INSTANCE;
    abstract InformationDao informationDao();
    //private static final String LOG_TAG = InformationDao.class.getSimpleName();


    // Room Callback
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };


    // Methods
    static InformationDatabase getDatabase(final Context context) {

        // If instance of the DB is null, sync and check again
        if (INSTANCE == null) {
            synchronized (InformationDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            InformationDatabase.class, "information_database")
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    void insertHabit(Habit habit) {
        INSTANCE.informationDao().insertHabit(habit);
    }

    List<Habit> getAllHabits() {
        return INSTANCE.informationDao().getAllHabits();
    }

    void insertApps(Apps apps) {
        INSTANCE.informationDao().insertApps(apps);
    }

    List<Apps> getAllApps() {
        return INSTANCE.informationDao().getAllApps();
    }

    void insertPreferredArticle(PreferredArticle preferredArticle) {
        INSTANCE.informationDao().insertPreferredArticle(preferredArticle);
    }

    List<PreferredArticle> getAllPreferredArticles() {
        return INSTANCE.informationDao().getAllPreferredArticles();
    }

    void insertCurrentActivity(RecognizedActivity recognizedActivity) {
        INSTANCE.informationDao().insertRecognizedActivity(recognizedActivity);
    }

    RecognizedActivity getCurrentActivity() {
        return INSTANCE.informationDao().getRecognizedActivity();
    }
}
