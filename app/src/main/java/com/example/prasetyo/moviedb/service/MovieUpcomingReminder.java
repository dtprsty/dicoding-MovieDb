package com.example.prasetyo.moviedb.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.prasetyo.moviedb.R;
import com.example.prasetyo.moviedb.model.Movie;
import com.example.prasetyo.moviedb.util.NotificationBuilder;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

public class MovieUpcomingReminder extends BroadcastReceiver {

    private static int notificationId = 2045;
    private static int UPCOMING_HOUR = 8;

    @Override public void onReceive(Context context, Intent intent) {
        Movie movie = new Gson().fromJson(intent.getStringExtra("movie"), Movie.class);
        NotificationBuilder.sendNotification(
                context, context.getString(R.string.upcoming_notification, movie.getTitle()),
                notificationId, movie);
    }

    public void setUpcomingAlarm(Context context, List<Movie> movies) {
        int notificationDelay = 0;
        Calendar alarm =  Calendar.getInstance();
        alarm.set(Calendar.HOUR_OF_DAY, UPCOMING_HOUR);
        alarm.set(Calendar.MINUTE, 0);
        alarm.set(Calendar.SECOND, 0);
        for (Movie movie: movies) {
            cancelUpcomingAlarm(context);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, MovieUpcomingReminder.class);
            intent.putExtra("movie", new Gson().toJson(movie));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            set(alarmManager, alarm, notificationDelay, pendingIntent);
            notificationId++;
            notificationDelay += 5000;
        }
    }

    public void cancelUpcomingAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, MovieUpcomingReminder.class);
        return PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public static void set(AlarmManager alarmManager, Calendar calendar, int timeIncrement, PendingIntent intent) {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + timeIncrement,
                    AlarmManager.INTERVAL_DAY,
                    intent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() + timeIncrement, intent);
        }
    }
}