package com.my.newproject19;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class NotificationScheduler {
    private Context context;
    private AlarmManager alarmManager;
    
    public NotificationScheduler(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }
    
    public void scheduleRepeatingNotification(int notificationId, int hour, int minute, 
                                            boolean[] daysOfWeek, String title, String message) {
        // Cancel any existing alarm with same ID
        cancelNotification(notificationId);
        
        for (int day = 0; day < daysOfWeek.length; day++) {
            if (daysOfWeek[day]) {
                scheduleForDay(notificationId, day, hour, minute, title, message);
            }
        }
        
        // Save the alarm configuration
        saveAlarmConfig(notificationId, hour, minute, daysOfWeek, title, message);
        
        Log.d("NotificationScheduler", "Repeating notification scheduled for specified days");
    }
    
    private void scheduleForDay(int notificationId, int dayOfWeek, int hour, int minute, 
                               String title, String message) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        // Set the day of week (Calendar.SUNDAY = 1, Calendar.SATURDAY = 7)
        int calendarDay = convertToCalendarDay(dayOfWeek);
        calendar.set(Calendar.DAY_OF_WEEK, calendarDay);
        
        // If the time has already passed this week, schedule for next week
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        }
        
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        intent.putExtra("notificationId", notificationId * 100 + dayOfWeek); // Unique ID for each day
        
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                notificationId * 100 + dayOfWeek, // Unique request code for each day
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        
        // Set repeating alarm for weekly
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        } else {
            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        }
        
        Log.d("NotificationScheduler", "Scheduled for day " + dayOfWeek + " at " + hour + ":" + minute);
    }
    
    private int convertToCalendarDay(int dayIndex) {
        // dayIndex: 0=Sunday, 1=Monday, ..., 6=Saturday
        switch (dayIndex) {
            case 0: return Calendar.SUNDAY;
            case 1: return Calendar.MONDAY;
            case 2: return Calendar.TUESDAY;
            case 3: return Calendar.WEDNESDAY;
            case 4: return Calendar.THURSDAY;
            case 5: return Calendar.FRIDAY;
            case 6: return Calendar.SATURDAY;
            default: return Calendar.SUNDAY;
        }
    }
    
    public void cancelNotification(int notificationId) {
        // Cancel alarms for all days of the week
        for (int day = 0; day < 7; day++) {
            Intent intent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context,
                    notificationId * 100 + day,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
        
        // Remove from saved alarms
        removeAlarmConfig(notificationId);
        
        Log.d("NotificationScheduler", "Notification cancelled: " + notificationId);
    }
    
    private void saveAlarmConfig(int id, int hour, int minute, boolean[] daysOfWeek, 
                                String title, String message) {
        try {
            JSONObject alarmObj = new JSONObject();
            alarmObj.put("id", id);
            alarmObj.put("hour", hour);
            alarmObj.put("minute", minute);
            alarmObj.put("title", title);
            alarmObj.put("message", message);
            
            JSONArray daysArray = new JSONArray();
            for (boolean day : daysOfWeek) {
                daysArray.put(day);
            }
            alarmObj.put("days", daysArray);
            
            SharedPreferences prefs = context.getSharedPreferences("ScheduledAlarms", Context.MODE_PRIVATE);
            Set<String> alarmSet = new HashSet<>(prefs.getStringSet("alarms", new HashSet<>()));
            
            // Remove any existing entry with same ID
            alarmSet.removeIf(alarm -> {
                try {
                    JSONObject obj = new JSONObject(alarm);
                    return obj.getInt("id") == id;
                } catch (JSONException e) {
                    return false;
                }
            });
            
            // Add new entry
            alarmSet.add(alarmObj.toString());
            
            prefs.edit().putStringSet("alarms", alarmSet).apply();
            
        } catch (JSONException e) {
            Log.e("NotificationScheduler", "Error saving alarm config: " + e.getMessage());
        }
    }
    
    private void removeAlarmConfig(int id) {
        SharedPreferences prefs = context.getSharedPreferences("ScheduledAlarms", Context.MODE_PRIVATE);
        Set<String> alarmSet = new HashSet<>(prefs.getStringSet("alarms", new HashSet<>()));
        
        alarmSet.removeIf(alarm -> {
            try {
                JSONObject obj = new JSONObject(alarm);
                return obj.getInt("id") == id;
            } catch (JSONException e) {
                return false;
            }
        });
        
        prefs.edit().putStringSet("alarms", alarmSet).apply();
    }
}