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
import android.view.View;
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
import java.util.regex.*;
import org.json.*;

public class ChekDialogFragmentActivity extends DialogFragment {
	
	private ChekDialogFragmentBinding binding;
	private double ceck = 0;
	
	private Intent i = new Intent();
	private SharedPreferences pass;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		binding = ChekDialogFragmentBinding.inflate(_inflater, _container, false);
		initialize(_savedInstanceState, binding.getRoot());
		initializeLogic();
		return binding.getRoot();
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		pass = getContext().getSharedPreferences("pas", Activity.MODE_PRIVATE);
		
		binding.circleimageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				switch((int)ceck) {
					case ((int)0): {
						ceck = 1;
						binding.circleimageview1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_eye_off));
						
						//code By Gymkhana Studio
						
						
						
						
						//Hide pass
						binding.etphone.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
						
						break;
					}
					case ((int)1): {
						ceck = 0;
						binding.circleimageview1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_eye));
						//Show passwo
						binding.etphone.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
						break;
					}
				}
			}
		});
		
		binding.button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (binding.etphone.getText().toString().equals("tsfa01")) {
					SketchwareUtil.showMessage(getContext().getApplicationContext(), "you're  Tesfatsion choir ");
					pass.edit().putString("pas", "tsfa01").commit();
					i.setClass(getContext().getApplicationContext(), MaineActivity.class);
					startActivity(i);
				} else {
					SketchwareUtil.showMessage(getContext().getApplicationContext(), "you're not Tesfatsion choir ");
				}
			}
		});
	}
	
	private void initializeLogic() {
		binding.etphone.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
	}
	
}