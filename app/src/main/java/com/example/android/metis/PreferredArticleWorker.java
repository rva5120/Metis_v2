package com.example.android.metis;

import android.content.Context;
import android.support.annotation.NonNull;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PreferredArticleWorker extends Worker {

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

            return Worker.Result.success();
        } catch (Throwable throwable) {
            return Worker.Result.failure();
        }
    }
}
