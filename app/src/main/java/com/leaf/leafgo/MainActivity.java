package com.leaf.leafgo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String CHANNEL_ID="MY APP NOTIFICATION 01";
    private NotificationManager notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "default", NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("Welcome To LEAF-GO");
            notificationChannel.setVibrationPattern(new long[]{1000,1000});
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle("Hello,There");
        builder.setContentText("Welcome To LEAF-GO");
        builder.setColor(Color.rgb(247,255,230));
        builder.setVibrate(new long[]{1000,1000});

//Adding LARGE ICON -> START
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        builder.setLargeIcon(bitmap);
//Adding LARGE ICON -> END

        Notification notification =builder.build();

        notificationManager.notify(0,notification);
    }
}