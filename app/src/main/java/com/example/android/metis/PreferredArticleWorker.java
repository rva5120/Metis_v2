package com.example.android.metis;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PreferredArticleWorker extends Worker {

    private static final String LOG = "METIS - DBG";

    public PreferredArticleWorker(@NonNull Context context,
                                  @NonNull WorkerParameters workerParameters) {
        super(context, workerParameters);
    }

    @NonNull
    @Override
    public Result doWork() {

        Context context = getApplicationContext();

        try {

            String timestamp = getInputData().getString("timestamp");
            if (timestamp == null) {
                timestamp = "Null?";
            }

            PreferredArticle preferredArticle = new PreferredArticle(
                    timestamp,
                    getInputData().getString("article"));

            InformationDatabase.getDatabase(context.getApplicationContext()).insertPreferredArticle(preferredArticle);

            Habit habit = WorkerUtils.getCurrentHabit(context, Boolean.FALSE);
            InformationDatabase.getDatabase(context.getApplicationContext()).insertHabit(habit);

            Log.d(LOG, "PREFERRED_ARTICLE_WORKER/: Saving a preferred article (plus habit)...");

            return Worker.Result.success();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return Worker.Result.failure();
        }
    }
}
