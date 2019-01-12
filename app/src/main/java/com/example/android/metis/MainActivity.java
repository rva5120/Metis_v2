package com.example.android.metis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // Attributes
    private InformationDatabase informationDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Instance of the Database
        informationDatabase = InformationDatabase.getDatabase(getApplicationContext());

        // Register Work Requests with the Manager
        registerPeriodicHabitsCollection();
        registerPeriodicAppsCollection();

        // Display the article
        displayArticle();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register Work Request to Indicate that the user is using the app
        registerOneTimeUsingAppHabitsCollection(Boolean.TRUE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Register Work Request to indicate that the user is no longer using the app
        registerOneTimeUsingAppHabitsCollection(Boolean.FALSE);
    }

    private void displayArticle() {

    }

    private void registerPeriodicHabitsCollection() {

    }

    private void registerPeriodicAppsCollection() {

    }

    private void registerOneTimeUsingAppHabitsCollection(Boolean usingApp) {

    }
}