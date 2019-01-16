package com.example.android.metis;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.Calendar;



public class ActivityRecognitionIntentService extends IntentService {

    // Constructor
    public ActivityRecognitionIntentService() {
        super("Custom Activity Recognition Intent Service");
    }

    // Methods
    @Override
    protected void onHandleIntent(Intent intent) {

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

                Log.d("AR INTENT SERVICE/", "AR Sent us an Updated Activity (added activity and habit to DB..)!!");
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
