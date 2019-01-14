package com.example.android.metis;

import android.content.Context;
import android.support.annotation.NonNull;

import androidx.work.Worker;
import androidx.work.WorkerParameters;


public class ActivityWorker extends Worker {

    // Constructor
    public ActivityWorker(@NonNull Context appContext,
                          @NonNull WorkerParameters workerParameters) {
        super(appContext, workerParameters);
    }

    // Task
    @NonNull
    @Override
    public Result doWork() {

        Context context = getApplicationContext();

        try {
            WorkerUtils.registerCurrentActivityListener(context);
            return Worker.Result.success();
        } catch (Throwable throwable) {
            return Worker.Result.failure();
        }
    }

}
