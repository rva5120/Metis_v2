package com.example.android.metis;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.Calendar;



public class ActivityRecognitionIntentService extends IntentService {

    private static final String LOG = "METIS - DBG (AR)";

    // Constructor
    public ActivityRecognitionIntentService() {
        super("Custom Activity Recognition Intent Service");
    }

    // Methods
    @Override
    protected void onHandleIntent(Intent intent) {

        if (ActivityTransitionResult.hasResult(intent)) {
            ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);

            if (result.getTransitionEvents() != null) {
                for (ActivityTransitionEvent event : result.getTransitionEvents()) {
                    Log.d(LOG, "AR TRANSITION RCVD: This is what we received..." + event.toString());

                    String activity = getStringActivity(event.getActivityType());
                    String confidence = "N/A_tran";

                    synchronized (InformationDatabase.getDatabase(getApplicationContext())) {

                        // Insert current activity on the DB
                        RecognizedActivity recognizedActivity = new RecognizedActivity(activity, confidence,
                                Calendar.getInstance().getTime().toString());
                        InformationDatabase.getDatabase(getApplicationContext()).insertCurrentActivity(recognizedActivity);

                        // Get Habit and store it!!
                        Habit habit = WorkerUtils.getCurrentHabit(getApplicationContext(), Boolean.FALSE);
                        InformationDatabase.getDatabase(getApplicationContext()).insertHabit(habit);

                        Log.d(LOG, "AR INTENT SERVICE//: AR Sent us a TRANSITION Activity (added activity and habit to DB..)!!");
                    }
                }
            }
        }

        if (ActivityRecognitionResult.hasResult(intent)) {

            ActivityRecognitionResult activityRecognitionResult = ActivityRecognitionResult.extractResult(intent);
            String activity = getStringActivity(activityRecognitionResult.getMostProbableActivity().getType());
            int confidence = activityRecognitionResult.getMostProbableActivity().getConfidence();

            synchronized (InformationDatabase.getDatabase(getApplicationContext())) {

                // Insert current activity on the DB
                RecognizedActivity recognizedActivity = new RecognizedActivity(activity, String.valueOf(confidence),
                        Calendar.getInstance().getTime().toString());
                InformationDatabase.getDatabase(getApplicationContext()).insertCurrentActivity(recognizedActivity);

                // Get Habit and store it!!
                Habit habit = WorkerUtils.getCurrentHabit(getApplicationContext(), Boolean.FALSE);
                InformationDatabase.getDatabase(getApplicationContext()).insertHabit(habit);

                Log.d(LOG, "AR INTENT SERVICE/: AR Sent us an Updated Activity (added activity and habit to DB..)!!");
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
