package com.example.android.metis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    // Attributes
    private InformationDatabase informationDatabase;
    private WorkManager mWorkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Instance of the Database
        //informationDatabase = InformationDatabase.getDatabase(getApplicationContext());

        // Work Manager Solution  - Part 1 (next: WorkerUtils)
        // 1. Check if the activity has been already recorded at some point FILE exists! (on our private dir)
        File file = new File(getApplicationContext().getFilesDir(), "checking.activity");
        if (file.exists()) {

            // We are good to go, no need to schedule anything else!

        } else {

            // Create a new file and schedule the Workers
            try {
                file.createNewFile();

                // Register Work Requests to get Activity Recognition updates, and to store Habits periodically
                Data.Builder builder = new Data.Builder();
                builder.putInt("APP_STATE", 0);
                OneTimeWorkRequest activityRecognitionStartListenerRequest = new OneTimeWorkRequest.Builder(ActivityWorker.class)
                        .setInputData(builder.build())
                        .build();
                mWorkManager.enqueue(activityRecognitionStartListenerRequest);

                PeriodicWorkRequest habitCollectionRequest = new PeriodicWorkRequest.Builder(HabitWorker.class, 1, TimeUnit.HOURS)
                        .setInputData(builder.build())
                        .build();
                mWorkManager.enqueue(habitCollectionRequest);

                PeriodicWorkRequest appsCollectionRequest = new PeriodicWorkRequest.Builder(AppsWorker.class, 5, TimeUnit.DAYS)
                        .build();
                mWorkManager.enqueue(appsCollectionRequest);

            } catch (IOException e ){
                e.printStackTrace();
            }

        }

        // Work Manager Solution - Part 5
        // -1. Schedule OneTime request to save habits during onPause w/ APP_STATE = 0 (done)
        // 0. Schedule OneTime request to save habits during onResume w/ APP_STATE = 1 (done)
        // 1. Display the article (done)
        // 2. Add a button that downloads article to the SD card
        // 3. Add a button that downloads the DB to a file and saves it to the SD card (almost done)
        // 4. Add a button to clear the DB (in case of space issues)
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

        // Create a notification to notify the user when the file is downloaded
        
        // Download the file

    }

    // SD Card download button

    // Delete DB, and possibly add stop data collection
}