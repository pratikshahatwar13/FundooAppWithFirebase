package com.bridgelabzs.add_note_page;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.bridgelabzs.fundooapp.R;

import static com.bridgelabzs.application.MyApplicationClass.CHANNEL_1_ID;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver.class";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notificationIntent = new Intent(context, AddNoteActivity.class);


        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(AddNoteActivity.class);
        taskStackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(100,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_1_ID);
        Notification notification = builder.setContentTitle("app notification")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("title")
                .setContentText("description")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

//        int notificationId = intent.getIntExtra("notificationId",0);
//        String message = intent.getStringExtra("todo");
//
//        Intent addNoteIntent = new Intent(context,AddNoteActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(context,0,addNoteIntent,0);
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//
//        //Prepare Notification
//        Notification.Builder builder = new Notification.Builder(context);
//        builder.setSmallIcon(R.mipmap.logo)
//                .setContentTitle("It's time")
//                .setContentText(message)
//                .setWhen(System.currentTimeMillis())
//                .setAutoCancel(true)
//                .setContentIntent(contentIntent)
//                .setPriority(Notification.PRIORITY_MAX)
//                .setDefaults(Notification.DEFAULT_ALL);
//
//        //notify
//        notificationManager.notify(notificationId,builder.build());


//        Intent notificationIntent = new Intent(context,AddNoteActivity.class);
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addParentStack(AddNoteActivity.class);
//        stackBuilder.addNextIntent(notificationIntent);
//
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(100,PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//
//        Notification notification = builder.setContentTitle("Notes Notification")
//                .setContentText("New Notification from fundoo App")
//                .setTicker("New Message Alert !")
//                .setAutoCancel(true)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentIntent(pendingIntent).build();
//
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0,notification);
    }
}
