package com.my.newproject19;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.appbar.AppBarLayout;
import com.my.newproject19.databinding.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;
import android.app.TimePickerDialog;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Random;

public class NotificationActivity extends AppCompatActivity {
	
	private NotificationBinding binding;
private NotificationScheduler notificationScheduler;
    private int selectedHour = -1;
    private int selectedMinute = -1;
    private int currentNotificationId = 1;
    private CheckBox[] dayCheckboxes;
    private final String[] DAYS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = NotificationBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		setSupportActionBar(binding.Toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		binding.Toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
	}
	
	private void initializeLogic() {
		
		
		notificationScheduler = new NotificationScheduler(this);
		
		Button btnSelectTime = findViewById(R.id.btnSelectTime);
		Button btnSchedule = findViewById(R.id.btnSchedule);
		Button btnCancel = findViewById(R.id.btnCancel);
		TextView tvSelectedTime = findViewById(R.id.tvSelectedTime);
		
		// Setup day checkboxes
		setupDayCheckboxes();
		
		btnSelectTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showTimePicker();
			}
		});
		
		btnSchedule.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (selectedHour != -1 && selectedMinute != -1) {
					if (isAnyDaySelected()) {
						scheduleNotification();
					} else {
						Toast.makeText(NotificationActivity.this, "Please select at least one day", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(NotificationActivity.this, "Please select a time first", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cancelNotification();
			}
		});
	}
	
  private void setupDayCheckboxes() {
        LinearLayout daysContainer = findViewById(R.id.daysContainer);
        dayCheckboxes = new CheckBox[7];
        
        for (int i = 0; i < 7; i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(DAYS[i]);
            checkBox.setId(View.generateViewId());
            daysContainer.addView(checkBox);
            dayCheckboxes[i] = checkBox;
        }
    }
    
    private boolean[] getSelectedDays() {
        boolean[] selectedDays = new boolean[7];
        for (int i = 0; i < 7; i++) {
            selectedDays[i] = dayCheckboxes[i].isChecked();
        }
        return selectedDays;
    }
    
    private boolean isAnyDaySelected() {
        for (CheckBox checkBox : dayCheckboxes) {
            if (checkBox.isChecked()) {
                return true;
            }
        }
        return false;
    }
    
    private void showTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedHour = hourOfDay;
                        selectedMinute = minute;
                        
                        TextView tvSelectedTime = findViewById(R.id.tvSelectedTime);
                        String timeText = String.format("Selected time: %02d:%02d", hourOfDay, minute);
                        tvSelectedTime.setText(timeText);
                    }
                },
                hour,
                minute,
                true
        );
        
        timePickerDialog.setTitle("Select Notification Time");
        timePickerDialog.show();
    }
    
    private void scheduleNotification() {
        // Generate a unique ID for this notification
        int notificationId = new Random().nextInt(1000) + 1;
        currentNotificationId = notificationId;
        
        boolean[] selectedDays = getSelectedDays();
        
        notificationScheduler.scheduleRepeatingNotification(
                notificationId,
                selectedHour,
                selectedMinute,
                selectedDays,
                "Scheduled Notification",
                "This is your scheduled notification for " + getSelectedDaysString(selectedDays) + 
                " at " + String.format("%02d:%02d", selectedHour, selectedMinute)
        );
        
        Toast.makeText(this, 
                "Notifications scheduled for " + getSelectedDaysString(selectedDays) + 
                " at " + String.format("%02d:%02d", selectedHour, selectedMinute), 
                Toast.LENGTH_LONG).show();
    }
    
    private String getSelectedDaysString(boolean[] selectedDays) {
        StringBuilder daysString = new StringBuilder();
        for (int i = 0; i < selectedDays.length; i++) {
            if (selectedDays[i]) {
                if (daysString.length() > 0) {
                    daysString.append(", ");
                }
                daysString.append(DAYS[i]);
            }
        }
        return daysString.toString();
    }
    
    private void cancelNotification() {
        notificationScheduler.cancelNotification(currentNotificationId);
        Toast.makeText(this, "Last notification cancelled", Toast.LENGTH_SHORT).show();
    
		
	}
	
}