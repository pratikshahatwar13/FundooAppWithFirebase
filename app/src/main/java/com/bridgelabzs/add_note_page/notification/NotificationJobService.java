package com.bridgelabzs.add_note_page.notification;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class NotificationJobService extends JobService {
    private static final String TAG = "NotificationJobService";
    private boolean jobCancelled = false;
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG,"Job Started");
        doBackgroundWork(params);
        return false;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(jobCancelled){
                    return;
                }
              jobFinished(params,false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e(TAG,"Job cancelled before completion");
        jobCancelled = true;
        return false;
    }
}
