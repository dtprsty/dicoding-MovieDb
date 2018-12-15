package com.example.prasetyo.moviedb.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.prasetyo.moviedb.R;
import com.example.prasetyo.moviedb.model.Movie;
import com.example.prasetyo.moviedb.ui.detail.DetailMovieActivity;
import com.example.prasetyo.moviedb.ui.main.MainActivity;

public class NotificationBuilder {

    public static void sendNotification(Context context, String description, int id) {
        Intent intent = new Intent(context, MainActivity.class);
        notification(context, intent, description, id);
    }

    public static void sendNotification(Context context, String description, int id, Movie movie) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra("movie", movie);
        notification(context, intent, description, id);
    }

    private static void notification(Context context, Intent intent, String description, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_added_to_fav)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(description)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("2407",
                    "NOTIFY_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);

            builder.setChannelId("2407");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(id, builder.build());
    }

}