package com.example.prasetyo.moviedb.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.prasetyo.moviedb.R;
import com.example.prasetyo.moviedb.util.NotificationBuilder;

import java.util.Calendar;

public class MovieDailyReminder extends BroadcastReceiver {

    private static int notificationId = Integer.valueOf("2407");
    private static int DAILY_HOUR = 7;

    @Override public void onReceive(Context context, Intent intent) {
        NotificationBuilder.sendNotification(context, context.getString(R.string.daily_notification), notificationId);
    }

    public void setDailyAlarm(Context context) {
        cancelDailyAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar alarm =  Calendar.getInstance();
        alarm.set(Calendar.HOUR_OF_DAY, DAILY_HOUR);
        alarm.set(Calendar.MINUTE, 0);
        alarm.set(Calendar.SECOND, 0);
        set(alarmManager, alarm, 1, getPendingIntent(context));
    }

    public void cancelDailyAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, MovieDailyReminder.class);
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
