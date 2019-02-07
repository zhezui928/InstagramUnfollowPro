package com.it_tech613.zhe.instagramunfollowpro.utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class WeeklyReceiver extends BroadcastReceiver {
    @SuppressLint("StaticFieldLeak")
    private static Context ctx;
    private static final int hourOfDay=8;
    private static final int minuteOfHour=0;
    public static final String CUSTOM_INTENT = "com.test.intent.action.WEEKLYALARM";
    @Override
    public void onReceive(final Context context, Intent intent) {
        //this will update the UI with message
        Log.e("WeeklyReceiver","received");
        if (PreferenceManager.is_rateus_noti_showed) return;
//        Intent serviceIntent = new Intent(context.getApplicationContext(),FloatingView.class);
//        serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(serviceIntent);
//        } else {
//            context.startService(serviceIntent);
//        }
        WeeklyService.enqueueWork(context,intent);
    }

    public static void cancelAlarm() {
        AlarmManager alarm = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        /* cancel any pending alarm */
        alarm.cancel(getPendingIntent());
    }

    public static void setCtx(Context context){
        ctx=context;
    }

    public static void setAlarm(boolean force) {
//        boolean alarmUp = (PendingIntent.getBroadcast(ctx, 0,
//                new Intent(CUSTOM_INTENT),
//                PendingIntent.FLAG_CANCEL_CURRENT) != null);
//
//        if (alarmUp) {
//            Log.e("WeeklyReceiver", "Alarm is already active");
//            return;
//        }
        cancelAlarm();
        AlarmManager alarm = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minuteOfHour);
        long when=calendar.getTimeInMillis();
        when=System.currentTimeMillis()+10000;
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, when,AlarmManager.INTERVAL_DAY*7, getPendingIntent());
        Log.e("WeeklyReceiver", "Alarm is set successfully");
    }
    private static PendingIntent getPendingIntent() {
        Intent alarmIntent = new Intent(ctx, WeeklyReceiver.class);
        alarmIntent.setAction(CUSTOM_INTENT);

        return PendingIntent.getBroadcast(ctx, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}