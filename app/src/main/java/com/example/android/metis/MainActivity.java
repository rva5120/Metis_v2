package com.example.android.metis;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.security.Permission;
import java.util.concurrent.TimeUnit;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    // Attributes
    private InformationDatabase informationDatabase;
    private WorkManager mWorkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ask for permissions!
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        }

        // Get Instance of WorkManager
        mWorkManager = WorkManager.getInstance();

        // Work Manager Solution  - Part 1 (next: WorkerUtils)
        // 1. Check if the activity has been already recorded at some point FILE exists! (on our private dir)
        File file = new File("/sdcard", "checking.activity");
        if (!file.exists()) {
            // Create a new file and schedule the Workers
            try {

                // Register Work Requests to get Activity Recognition updates, and to store Habits periodically
                Data.Builder builder = new Data.Builder();
                builder.putInt("APP_STATE", 0);
                OneTimeWorkRequest activityRecognitionStartListenerRequest = new OneTimeWorkRequest.Builder(ActivityWorker.class)
                        .setInputData(builder.build())
                        .build();
                mWorkManager.enqueue(activityRecognitionStartListenerRequest);

                PeriodicWorkRequest habitCollectionRequest = new PeriodicWorkRequest.Builder(HabitWorker.class, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
                        .build();
                mWorkManager.enqueue(habitCollectionRequest);

                PeriodicWorkRequest appsCollectionRequest = new PeriodicWorkRequest.Builder(AppsWorker.class, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
                        .build();
                mWorkManager.enqueue(appsCollectionRequest);

                file.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Register Work Request to Indicate that the user is using the app
        Data.Builder data = new Data.Builder();
        data.putInt("APP_STATE", 1);
        OneTimeWorkRequest appUsageCollectionWorkRequest = new OneTimeWorkRequest.Builder(HabitWorker.class)
                .setInputData(data.build())
                .build();
        mWorkManager.enqueue(appUsageCollectionWorkRequest);

        // Display the article
        displayArticle();
    }


    @Override
    protected void onPause() {
        super.onPause();

        // Register Work Request to indicate that the user is no longer using the app
        Data.Builder data = new Data.Builder();
        data.putInt("APP_STATE", 0);
        OneTimeWorkRequest appNotUsingCollectionWorkRequest = new OneTimeWorkRequest.Builder(HabitWorker.class)
                .setInputData(data.build())
                .build();
        mWorkManager.enqueue(appNotUsingCollectionWorkRequest);
    }


    private void displayArticle() {

        String articleURL = "https://rva5120.github.io/tutorials/2018/02/26/introductory-tutorial";

        WebView webView = findViewById(R.id.webview);
        webView.loadUrl(articleURL);
    }


    public void downloadFile(View view) {

        Toast.makeText(this, "Downloading file... Please Wait.", Toast.LENGTH_LONG).show();

        // Start a Worker to download the files and notify when done
        OneTimeWorkRequest downloadDbWorkRequest = new OneTimeWorkRequest.Builder(DumpDBToFileWorker.class)
                .build();
        mWorkManager.enqueue(downloadDbWorkRequest);

    }

    // Work Manager Solution - Part 5
    // 2. Add a button that downloads article to the SD card
    // 4. Add a button to clear the DB (in case of space issues)

    // SD Card download button

    // Delete DB, and possibly add stop data collection
}