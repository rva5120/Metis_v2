package com.example.android.metis;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class HabitWorker extends Worker {

    // Constructor
    public HabitWorker(@NonNull Context appContext,
                       @NonNull WorkerParameters workerParameters) {
        super(appContext, workerParameters);
    }

    // Task
    @NonNull
    @Override
    public Result doWork() {

        // 1. Get the Application context
        Context context = getApplicationContext();

        try {
            // 2. Get the current habit
            Boolean appState;
            Boolean receivedData = getInputData().getKeyValueMap().isEmpty();
            // If we received data, then read it (otherwise we didn't
            // which means the user is not on the app at the moment)
            if (receivedData) {
                if (getInputData().getInt("APP_STATE", 0) == 0) {
                    appState = Boolean.FALSE;
                } else {
                    appState = Boolean.TRUE;
                }
            } else {
                appState = Boolean.FALSE;
            }
            Habit habit = WorkerUtils.getCurrentHabit(context, appState);

            // 3. Store habit on the DB
            InformationDatabase informationDatabase = InformationDatabase.getDatabase(getApplicationContext());
            informationDatabase.insertHabit(habit);

            Log.d("HABIT_WORKER/", "Recorded a habit!!");

            return Worker.Result.success();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return Worker.Result.failure();
        }
    }
}
