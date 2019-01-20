package com.example.android.metis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.Calendar;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class ActivityRecognitionBroadcastReceiver extends BroadcastReceiver {

    private static final String LOG = "METIS - DBG (AR BR)";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (ActivityTransitionResult.hasResult(intent)) {
            ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);

            if (result.getTransitionEvents() != null) {

                for (ActivityTransitionEvent event : result.getTransitionEvents()) {
                    Log.d(LOG, "AR TRANSITION RCVD: This is what we received..." + event.toString());

                    String activity = getStringActivity(event.getActivityType());
                    String confidence = "N/A_tran";
                    //String timestamp = event.getElapsedRealTimeNanos() / ;

                    synchronized (InformationDatabase.getDatabase(context)) {

                        // Insert current activity on the DB
                        RecognizedActivity recognizedActivity = new RecognizedActivity(activity, confidence,
                                Calendar.getInstance().getTime().toString());
                        InformationDatabase.getDatabase(context).insertCurrentActivity(recognizedActivity);

                        // Get Habit and store it!!
                        OneTimeWorkRequest appsCollectionRequest = new OneTimeWorkRequest.Builder(AppsWorker.class)
                                .build();
                        WorkManager.getInstance().enqueue(appsCollectionRequest);

                        Log.d(LOG, "AR BROADCAST RECEIVER//: AR Sent us a TRANSITION Activity (added activity and habit to DB..)!!");
                    }
                }
            }
        }

        if (ActivityRecognitionResult.hasResult(intent)) {

            ActivityRecognitionResult activityRecognitionResult = ActivityRecognitionResult.extractResult(intent);
            String activity = getStringActivity(activityRecognitionResult.getMostProbableActivity().getType());
            int confidence = activityRecognitionResult.getMostProbableActivity().getConfidence();

            synchronized (InformationDatabase.getDatabase(context)) {

                // Insert current activity on the DB
                RecognizedActivity recognizedActivity = new RecognizedActivity(activity, String.valueOf(confidence),
                        Calendar.getInstance().getTime().toString());
                InformationDatabase.getDatabase(context).insertCurrentActivity(recognizedActivity);

                // Get Habit and store it!!
                OneTimeWorkRequest appsCollectionRequest = new OneTimeWorkRequest.Builder(AppsWorker.class)
                        .build();
                WorkManager.getInstance().enqueue(appsCollectionRequest);

                Log.d(LOG, "AR BROADCAST RECEIVER/: AR Sent us an Updated Activity (added activity and habit to DB..)!!");
            }
        }
    }

    private String getStringActivity(int type) {

        if (type == DetectedActivity.IN_VEHICLE) {
            return "IN_VEHICLE";
        } else if (type == DetectedActivity.ON_BICYCLE) {
            return "ON BICYCLE";
        } else if (type == DetectedActivity.ON_FOOT) {
            return "ON FOOT";
        } else if (type == DetectedActivity.STILL) {
            return "STILL";
        } else if (type == DetectedActivity.UNKNOWN) {
            return "UNKNOWN";
        } else if (type == DetectedActivity.TILTING) {
            return "TILTING";
        } else if (type == DetectedActivity.WALKING) {
            return "WALKING";
        } else if (type == DetectedActivity.RUNNING) {
            return "RUNNING";
        } else {
            return "DOES NOT MAP TO AN EXISTING ACTIVITY...";
        }
    }
}
