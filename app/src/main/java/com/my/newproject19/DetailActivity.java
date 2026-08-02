package com.my.newproject19;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
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
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.my.newproject19.databinding.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import android.view.WindowManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;

public class DetailActivity extends AppCompatActivity {
	
	private DetailBinding binding;
private final String fileName = "kid.json";
//private String song;
	private double post = 0;
	private String song = "";
	
	private ArrayList<HashMap<String, Object>> songli = new ArrayList<>();
	
	private Intent intr = new Intent();
	private SharedPreferences setting;
	private SharedPreferences oo;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = DetailBinding.inflate(getLayoutInflater());
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
		setting = getSharedPreferences("settng", Activity.MODE_PRIVATE);
		oo = getSharedPreferences("oo", Activity.MODE_PRIVATE);
	}
	
	private void initializeLogic() {
		setTitle("Songs Detail ");
		songli.clear();
		String song = readFromInternalStorage();
		
		if (song.isEmpty()) {
			SketchwareUtil.showMessage(getApplicationContext(), "no songs found!");
			return;
		}
		songli = new Gson().fromJson(song, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		post = Double.parseDouble(getIntent().getStringExtra("post"));
		binding.textview1.setText(songli.get((int)post).get("name").toString());
		binding.textview2.setText(songli.get((int)post).get("detail").toString());
		binding.textview3.setText(songli.get((int)post).get("id").toString());
		_font();
		// In your onCreate or where you want to set the gradient
		// Replace with your View ID
		
		// Define your 4 colors
		int[] color= {
			Color.parseColor("#FFffff"),
			Color.parseColor("#FFffff"), // Red
			Color.parseColor("#FFffff"),
			Color.parseColor("#FFffff"),//
			Color.parseColor("#FFffff"),//
			Color.parseColor("#FFffff"), // Dark blue
			Color.parseColor("#C0C0C0")  // Pink
		};
		
		// Create gradient
		GradientDrawable gradien = new GradientDrawable(
		GradientDrawable.Orientation.TL_BR, // Top-left to bottom-right
		color
		);
		
		// Set shape and corners
		gradien.setShape(GradientDrawable.RECTANGLE);
		gradien.setCornerRadius(16f); // Optional: rounded corners
		
		// Apply gradient to view
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			binding.vscroll1.setBackground(gradien);
		} else {
			binding.vscroll1.setBackgroundDrawable(gradien);
		}
	}
	
	public void _font() {
		if (oo.getString("oo", "").equals("")) {
			binding.textview2.setTextSize((int)25);
		} else {
			binding.textview2.setTextSize((int)Integer.parseInt(oo.getString("oo", "")));
		}
		if (oo.getString("scr", "").equals("scr")) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		} else {
			
		}
		if (oo.getString("ful", "").equals("ful")) {
			
			// Full screen mode
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
			
			// Keep screen always on
			
			
			// Optional: Hide navigation bar (for immersive experience)
			getWindow().getDecorView().setSystemUiVisibility(
			View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
			View.SYSTEM_UI_FLAG_FULLSCREEN |
			View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
			);
		} else {
			
		}
		if (oo.getString("tft", "").equals("al")) {
			binding.textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/iret.ttf"), 1);
		} else {
			if (oo.getString("tft", "").equals("all")) {
				binding.textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/antuwua.ttf"), 1);
			} else {
				if (oo.getString("tft", "").equals("alll")) {
					binding.textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/igezubisratgothic.ttf"), 1);
				} else {
					if (oo.getString("tft", "").equals("allll")) {
						binding.textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/elan.ttf"), 1);
					} else {
						binding.textview2.setTypeface(Typeface.DEFAULT, 1);
					}
				}
			}
		}
	}
	
	

    
    
    
    private String readFromInternalStorage() {
        try {
            FileInputStream fis = openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            
            reader.close();
            fis.close();
            return stringBuilder.toString();
            
        } catch (Exception e) {
            //showToast("Error reading file: " + e.getMessage());
            return "";
        }
    

		
	}
	
}