package com.example.android.metis;

import android.content.Context;
import android.support.annotation.NonNull;

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
            if (getInputData().getInt("APP_STATE", 0) == 0) {
                appState = Boolean.FALSE;
            } else {
                appState = Boolean.TRUE;
            }
            Habit habit = WorkerUtils.getCurrentHabit(context, appState);

            // 3. Store habit on the DB
            InformationDatabase informationDatabase = InformationDatabase.getDatabase(getApplicationContext());
            informationDatabase.insertHabit(habit);

            return Worker.Result.success();
        } catch (Throwable throwable) {
            return Worker.Result.failure();
        }
    }
}
