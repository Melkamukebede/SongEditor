package com.my.newproject19;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d("BootReceiver", "Device boot completed, rescheduling alarms");
            rescheduleAlarms(context);
        }
    }
    
    private void rescheduleAlarms(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("ScheduledAlarms", Context.MODE_PRIVATE);
        Set<String> alarmSet = prefs.getStringSet("alarms", new HashSet<>());
        
        NotificationScheduler scheduler = new NotificationScheduler(context);
        
        for (String alarmJson : alarmSet) {
            try {
                JSONObject alarmObj = new JSONObject(alarmJson);
                int id = alarmObj.getInt("id");
                int hour = alarmObj.getInt("hour");
                int minute = alarmObj.getInt("minute");
                String title = alarmObj.getString("title");
                String message = alarmObj.getString("message");
                boolean[] daysOfWeek = jsonArrayToBooleanArray(alarmObj.getJSONArray("days"));
                
                scheduler.scheduleRepeatingNotification(id, hour, minute, daysOfWeek, title, message);
                
            } catch (JSONException e) {
                Log.e("BootReceiver", "Error parsing alarm JSON: " + e.getMessage());
            }
        }
    }
    
    private boolean[] jsonArrayToBooleanArray(JSONArray jsonArray) throws JSONException {
        boolean[] array = new boolean[7];
        for (int i = 0; i < 7; i++) {
            array[i] = jsonArray.getBoolean(i);
        }
        return array;
    }
}