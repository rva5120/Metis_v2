package com.example.android.metis;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import androidx.work.Worker;
import androidx.work.WorkerParameters;


// Work Manager Solution - Part 5 (next: MainActivity)
public class DumpDBToFileWorker extends Worker {

    public DumpDBToFileWorker(@NonNull Context context,
                              @NonNull WorkerParameters workerParameters) {
        super(context, workerParameters);
    }

    @Override
    @NonNull
    public Result doWork() {

        Context context = getApplicationContext();
        InformationDatabase database = InformationDatabase.getDatabase(context);

        try {

            // ** Habits Table **
            File file = new File("/sdcard", "habits.csv");
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            List<Habit> habits = database.getAllHabits();
            for (Habit habit: habits) {
                String s = habit.getTimestamp() + "," +
                        habit.getid() + "," +
                        habit.getCity() + "," +
                        habit.getstate() + "," +
                        habit.getOnWiFi() + "," +
                        habit.getOrganisation() + "," +
                        habit.getCarrier() + "," +
                        habit.getBatteryState() + "," +
                        habit.getActivityTimestamp() + "," +
                        habit.getCurrentActivity() + "," +
                        habit.getActivityConfidence() + "," +
                        habit.getAppState() + "\n";
                 outputStreamWriter.append(s);
            }
            outputStreamWriter.close();
            fileOutputStream.flush();
            fileOutputStream.close();

            // ** Apps Table **
            File fileApps = new File("/sdcard", "apps.csv");
            fileApps.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            List<Apps> apps = database.getAllApps();
            for (Apps app: apps) {
                String s = app.getTimestamp() + "," + app.getApps() + "/n";
                outputStreamWriter.append(s);
            }
            outputStreamWriter.close();
            fileOutputStream.flush();
            fileOutputStream.close();

            // ** Preferred Articles Table **
            File preferredArticlesFile = new File("/sdcard", "preferredArticles.csv");
            preferredArticlesFile.createNewFile();
            fileOutputStream = new FileOutputStream(preferredArticlesFile);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            List<PreferredArticle> preferredArticles = database.getAllPreferredArticles();
            for (PreferredArticle article: preferredArticles) {
                String s = article.getTimestamp() + "," + article.getApps() + "/n";
                outputStreamWriter.write(s);
            }
            outputStreamWriter.close();
            fileOutputStream.flush();
            fileOutputStream.close();

            // ** Notify the user that the download is complete! **
            // 1. Create a notification channel
            final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(
                        PRIMARY_CHANNEL_ID,
                        "Download DB Channel",
                        NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.MAGENTA);
                notificationChannel.enableVibration(true);
                notificationChannel.setDescription("Notification from Metis about downloaded DB files");
                notificationManager.createNotificationChannel(notificationChannel);
            }
            // 2. Make a Notification Builder
            NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(getApplicationContext(), PRIMARY_CHANNEL_ID)
                    .setContentTitle("Metis Data Download Notification")
                    .setContentText("The database CSV files are now available on your SD Card!")
                    .setSmallIcon(R.drawable.ic_stat_name);
            // 3. Send notification
            notificationManager.notify(0, notifyBuilder.build());

            return Worker.Result.success();

        } catch (IOException e) {
            e.printStackTrace();
            return Worker.Result.failure();
        } catch (Throwable throwable) {
            return Worker.Result.failure();
        }

    }
}
