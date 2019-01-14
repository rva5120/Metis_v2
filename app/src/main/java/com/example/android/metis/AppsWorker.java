package com.example.android.metis;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.List;

import androidx.work.Worker;
import androidx.work.WorkerParameters;


// Work Manager Solution - Part 3 (next: PreferredArticleWorker)
// 1. Setup the worker to update the DB with the apps

public class AppsWorker extends Worker {

    public AppsWorker(@NonNull Context context,
                      @NonNull WorkerParameters workerParameters) {
        super(context, workerParameters);
    }


    @NonNull
    @Override
    public Result doWork() {

        Context context = getApplicationContext();

        try {

            // Get list of installed apps
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> appsList = context.getPackageManager().queryIntentActivities(intent, 0);

            // Get the app's names
            StringBuilder stringBuilder = new StringBuilder();
            for (ResolveInfo info: appsList) {
                stringBuilder.append((String) info.loadLabel(context.getPackageManager()));
                stringBuilder.append(",");
            }
            Apps apps = new Apps(Calendar.getInstance().getTime().toString(), stringBuilder.toString());

            // Send the apps record to the DB
            InformationDatabase database = InformationDatabase.getDatabase(context);
            database.informationDao().insertApps(apps);

            return Worker.Result.success();

        } catch (Throwable throwable) {
            return Worker.Result.failure();
        }
    }

}
