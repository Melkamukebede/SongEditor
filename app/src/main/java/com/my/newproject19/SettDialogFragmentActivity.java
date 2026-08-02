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
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.CompoundButton;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.my.newproject19.databinding.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;

public class SettDialogFragmentActivity extends DialogFragment {
	
	private SettDialogFragmentBinding binding;
	
	private SharedPreferences setting;
	private Intent intr = new Intent();
	private SharedPreferences oo;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		binding = SettDialogFragmentBinding.inflate(_inflater, _container, false);
		initialize(_savedInstanceState, binding.getRoot());
		initializeLogic();
		return binding.getRoot();
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		setting = getContext().getSharedPreferences("settng ", Activity.MODE_PRIVATE);
		oo = getContext().getSharedPreferences("oo", Activity.MODE_PRIVATE);
		
		binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (binding.switch1.isChecked()) {
					binding.switch1.setChecked(true);
					oo.edit().putString("scr", "scr").commit();
				} else {
					binding.switch1.setChecked(false);
					oo.edit().putString("scr", "").commit();
				}
			}
		});
		
		binding.switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (binding.switch2.isChecked()) {
					binding.switch2.setChecked(true);
					oo.edit().putString("ful", "ful").commit();
				} else {
					binding.switch2.setChecked(false);
					oo.edit().putString("ful", "").commit();
				}
			}
		});
		
		binding.switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				oo.edit().putString("tft", "al").commit();
				binding.switch4.setChecked(false);
				binding.switch5.setChecked(false);
				binding.switch6.setChecked(false);
			}
		});
		
		binding.switch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				oo.edit().putString("tft", "all").commit();
				binding.switch6.setChecked(false);
				binding.switch5.setChecked(false);
				binding.switch3.setChecked(false);
			}
		});
		
		binding.switch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				oo.edit().putString("tft", "alll").commit();
				binding.switch4.setChecked(false);
				binding.switch6.setChecked(false);
				binding.switch3.setChecked(false);
			}
		});
		
		binding.switch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				oo.edit().putString("tft", "allll").commit();
				binding.switch4.setChecked(false);
				binding.switch5.setChecked(false);
				binding.switch3.setChecked(false);
			}
		});
		
		binding.seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;
				oo.edit().putString("oo", String.valueOf((long)(_progressValue))).commit();
				binding.textview3.setTextSize((int)_progressValue);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar _param1) {
				
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar _param2) {
				
			}
		});
		
		binding.materialbutton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intr.setClass(getContext().getApplicationContext(), MaineActivity.class);
				startActivity(intr);
			}
		});
	}
	
	private void initializeLogic() {
		if (!oo.getString("oo", "").equals("")) {
			binding.seekbar1.setProgress((int)Integer.parseInt(oo.getString("oo", "")));
		}
		if (oo.getString("ful", "").equals("ful")) {
			binding.switch2.setChecked(true);
		}
		if (oo.getString("scr", "").equals("scr")) {
			binding.switch1.setChecked(true);
		}
		if (oo.getString("tft", "").equals("al")) {
			binding.switch3.setChecked(true);
		}
		if (oo.getString("tft", "").equals("all")) {
			binding.switch4.setChecked(true);
		}
		if (oo.getString("tft", "").equals("alll")) {
			binding.switch5.setChecked(true);
		}
		if (oo.getString("tft", "").equals("allll")) {
			binding.switch6.setChecked(true);
		}
		binding.switch3.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/iret.ttf"), 1);
		binding.switch4.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/antuwua.ttf"), 1);
		binding.switch5.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/igezubisratgothic.ttf"), 1);
		binding.switch6.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/elan.ttf"), 1);
	}
	
}