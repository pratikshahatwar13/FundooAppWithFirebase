package com.bridgelabzs.application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApplicationClass extends Application {
    public static final String CHANNEL_1_ID = "CHANNEL1";
    public static final String TAG = "MyApplicationClass";


    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("This is notification 1");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
//        FirebaseMessaging.getInstance().subscribeToTopic("general")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = "Successful";
//                        if (!task.isSuccessful()) {
//                            msg = "Failed";
//                        }
//                        Toast.makeText(MyApplicationClass.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });

    }

}
