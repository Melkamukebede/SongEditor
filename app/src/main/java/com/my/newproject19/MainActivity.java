package com.my.newproject19;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.my.newproject19.databinding.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private MainBinding binding;
	private String song = "";
	private String str = "";
	private String detail = "";
	private MyRecyclerViewAdapter adapter;
	
	private ArrayList<HashMap<String, Object>> songt = new ArrayList<>();
	private  ArrayList<DataObject> dataList = new ArrayList<>();
	
	private Intent intr = new Intent();
	private TimerTask tm;
	private SharedPreferences pass;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = MainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		pass = getSharedPreferences("pas", Activity.MODE_PRIVATE);
	}
	
	private void initializeLogic() {
		// For Java (in your Activity)
		View decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
		| View.SYSTEM_UI_FLAG_FULLSCREEN
		| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
		decorView.setSystemUiVisibility(uiOptions);
		new Handler(Looper.getMainLooper()).postDelayed(() -> {
			if (!isFinishing() && !isDestroyed()) {
				if (pass.getString("pas", "").equals("tsfa01")) {
					intr.setClass(getApplicationContext(), MaineActivity.class);
					startActivity(intr);
					finish();
				} else {
					ChekDialogFragmentActivityN = new ChekDialogFragmentActivity();
					ChekDialogFragmentActivityN.show(getSupportFragmentManager(),"1");
				}
			} else {
				Log.d("ActivityFlow", "Activity is finishing/destroyed, intent not fired");
			}
		}, 2000);
	}
	
	public void _booo() {
	}
	private ChekDialogFragmentActivity ChekDialogFragmentActivityN;
	private FragmentManager ChekDialogFragmentActivityFM;
	public void test_ChekDialogFragmentActivity () {
	}
	
	
	public void _background_activity(final boolean _run) {
		
	}
	
	
	public void _notify_user(final String _title, final String _description) {
		
	}
	
}