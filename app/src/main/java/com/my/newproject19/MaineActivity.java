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
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.LinearLayout;
import androidx.annotation.*;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.*;
import com.my.newproject19.databinding.*;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import android.content.Context;
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

public class MaineActivity extends AppCompatActivity {
	
	private MaineBinding binding;
private RecyclerView MyRecyclerView;
    private final String fileName = "kid.json";
    private EditText editTextSongName, editTextSongDetail;
    private Button buttonUpdate, buttonAdd;
private String deta;
	private String song = "";
	private String str = "";
	private String detail = "";
	private MyRecyclerViewAdapter adapter;
	private Context context;
	private double Json_show = 0;
	
	private ArrayList<HashMap<String, Object>> songt = new ArrayList<>();
	private  ArrayList<DataObject>  dataList = new ArrayList<>();
	private  ArrayList<DataObject>   filter = new ArrayList<>();
	
	private CardView _drawer_cardview2;
	private LinearLayout _drawer_linear2;
	private LinearLayout _drawer_linear11;
	private CircleImageView _drawer_circleimageview9;
	private LinearLayout _drawer_linear3;
	private LinearLayout _drawer_linear4;
	private LinearLayout _drawer_linear5;
	private CircleImageView _drawer_circleimageview2;
	private MaterialButton _drawer_open_song;
	private CircleImageView _drawer_circleimageview3;
	private MaterialButton _drawer_admin;
	private CircleImageView _drawer_circleimageview4;
	private MaterialButton _drawer_notf;
	
	private Intent intr = new Intent();
	private SharedPreferences setting;
	private SharedPreferences oo;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = MaineBinding.inflate(getLayoutInflater());
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
		ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(MaineActivity.this, binding.Drawer, binding.Toolbar, R.string.app_name, R.string.app_name);
		binding.Drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		setting = getSharedPreferences("settng", Activity.MODE_PRIVATE);
		oo = getSharedPreferences("oo", Activity.MODE_PRIVATE);
		
		binding.edittext1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				// Don't use dataList.clear() - it will clear your original data!
				// Instead, use a separate filtered list
				//final Context activityContext = this;
				ArrayList<DataObject> filter= new ArrayList<>();
				String searchText = binding.edittext1.getText().toString().toLowerCase().trim();
				
				if (searchText.isEmpty()) {
					// Show all items
					filter.addAll(dataList);
				} else {
					// Filter items
					for (int i = 0; i < dataList.size(); i++) {
						DataObject item = dataList.get(i);
						String songName = item.getmText1().toLowerCase();
						String artistName = item.getmText2().toLowerCase();
						
						if (songName.contains(searchText) || artistName.contains(searchText)) {
							filter.add(item);
						}
					}
				}
				
				// Update adapter with filtered list
				adapter = new MyRecyclerViewAdapter(filter, MaineActivity.this);
				binding.MyRecyclerView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		binding.fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Json_show++;
				if (Json_show > 6) {
					binding.json.setVisibility(View.VISIBLE);
				}
				if (Json_show > 10) {
					binding.json.setVisibility(View.GONE);
				}
			}
		});
		
		binding.btnShowFavorites.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				binding.btnShowFavorites.animate()
				.scaleX(0.85f)
				.scaleY(0.75f)
				.setDuration(80)
				.withEndAction(() -> {
					binding.btnShowFavorites.animate()
					.scaleX(1f)
					.scaleY(1f)
					.setDuration(80)
					.start();
				});
			}
		});
		
		binding.btnClearFavorites.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				binding.btnClearFavorites.animate()
				.scaleX(0.85f)
				.scaleY(0.75f)
				.setDuration(80)
				.withEndAction(() -> {
					binding.btnClearFavorites.animate()
					.scaleX(1f)
					.scaleY(1f)
					.setDuration(80)
					.start();
				});
			}
		});
		
		binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				binding.buttonUpdate.animate()
				.scaleX(0.85f)
				.scaleY(0.75f)
				.setDuration(80)
				.withEndAction(() -> {
					binding.buttonUpdate.animate()
					.scaleX(1f)
					.scaleY(1f)
					.setDuration(80)
					.start();
				});
			}
		});
		
		binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				binding.buttonAdd.animate()
				.scaleX(0.85f)
				.scaleY(0.75f)
				.setDuration(80)
				.withEndAction(() -> {
					binding.buttonAdd.animate()
					.scaleX(1f)
					.scaleY(1f)
					.setDuration(80)
					.start();
				});
			}
		});
		
		binding.drawer.notf.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intr.setClass(getApplicationContext(), NotificationActivity.class);
				startActivity(intr);
			}
		});
	}
	
	private void initializeLogic() {
		setTitle("All Songs ");
		binding.json.setVisibility(View.GONE);
		try {
			// Check if file already exists in internal storage
			String[] files = fileList();
			boolean fileExists = false;
			for (String file : files) {
				if (file.equals(fileName)) {
					fileExists = true;
					break;
				}
			}
			
			if (!fileExists) {
				InputStream inputStream = getAssets().open("kid.json");
				FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
				
				byte[] buffer = new byte[1024];
				int length;
				while ((length = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, length);
				}
				
				inputStream.close();
				outputStream.close();
				showToast("Songs loaded successfully");
			}
		} catch (Exception e) {
			showToast("Error loading songs: " + e.getMessage());
		}
		setupRecyclerView();
		setupButtonListeners();
		copyFileFromAssets();
		loadSongs();
		//setupButtonClickListeners();
		// Show loading message
		//showFavoriteCount();
		editTextSongName = findViewById(R.id.editTextSongName);
		editTextSongDetail = findViewById(R.id.editTextSongDetail);
		
			
		}
		
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			MenuItem men = menu.add(Menu.NONE, 0, Menu.NONE, "setting");
			men.setIcon(R.drawable.icon_settings_round);
			men.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			return super.onCreateOptionsMenu(menu);
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			final int _id = item.getItemId();
			final String _title = (String) item.getTitle();
			if (_id == 0) {
				SettDialogFragmentActivityN = new SettDialogFragmentActivity();
				SettDialogFragmentActivityN.show(getSupportFragmentManager(),"1");
			}
			return super.onOptionsItemSelected(item);
		}
		
		@Override
		public void onBackPressed() {
			if (hasFavorites()) {
				Toast.makeText(this, 
				"You have " + getFavoriteCount() + " favorite" + 
				(getFavoriteCount() > 1 ? "s" : ""), 
				Toast.LENGTH_SHORT).show();
			}
			finishAffinity();
		}
private void copyFileFromAssets() {
        try {
            // Check if file already exists in internal storage
            String[] files = fileList();
            boolean fileExists = false;
            for (String file : files) {
                if (file.equals(fileName)) {
                    fileExists = true;
                    break;
                }
            }
            
            if (!fileExists) {
                InputStream inputStream = getAssets().open("kid.json");
                FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                
                inputStream.close();
                outputStream.close();
                showToast("Songs loaded successfully");
            }
        } catch (Exception e) {
            showToast("Error loading songs: " + e.getMessage());
        }
    }
    
    
    
    
private void setupRecyclerView() {
      
  // Create sample data
            
           
//adapter 
adapter = new MyRecyclerViewAdapter(dataList, this);
binding.MyRecyclerView.setAdapter(adapter);
binding.MyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

             adapter.setOnItemClickListener(new MyRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {      //get item dat of dataList at position 
                DataObject item = dataList.get(position);
                handleItemClick(position, item, v);
            }
        });
adapter.setOnItemLongClickListener(new MyRecyclerViewAdapter.MyLongClickListener (){
@Override
    public boolean onItemLongClick(int position, View v) {
        DataObject item = adapter.getItem(position);
        if (item != null) {
            // Fill the edit texts with item data (like your example)
            editTextSongName.setText(item.getmText1());
            editTextSongDetail.setText(item.getmText2());
            
            // Show a message
            
        showToast("Editing" +item.getmText1());    
          return true;
  // Return true to indicate the long click was handled
           }
return true ;
}
   });

  }  
    private void handleItemClick(int position, DataObject item, View v) {
        // Show toast
        //Toast.makeText(this, "Clicked: " + item.getmText2(), Toast.LENGTH_SHORT).show();
       oo.edit().putString("pos", String.valueOf((long)(position))).commit(); 
  intr.putExtra("post", String.valueOf((long)(position)));
intr.setClass(getApplicationContext(), DetailActivity.class);
startActivity(intr);      // 
        
        }
    
// favorite manipulation 
    private void setupButtonListeners() {
    // Show favorites button
     findViewById(R.id.btn_show_favorites).setOnClickListener(v -> showFavoritesList());
    
    // Clear favorites button
 findViewById(R.id.btn_clear_favorites).setOnClickListener(v -> clearAllFavorites());

findViewById(R.id.buttonUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String songName = editTextSongName.getText().toString().trim();
                String songDetail = editTextSongDetail.getText().toString().trim();
                
                if (!songName.isEmpty() && !songDetail.isEmpty()) {
                    updateSong(songName, songDetail);
                } else {
                    showToast("Please enter both song name and details");
                }
            }
        });
        
        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String songName = editTextSongName.getText().toString().trim();
                String songDetail = editTextSongDetail.getText().toString().trim();
                
                if (!songName.isEmpty() && !songDetail.isEmpty()) {
                    addNewSong(songName, songDetail);
                } else {
                    showToast("Please enter both song name and details");
                }
            }
        });
}

    
    /**
     * Get all favorite items from the current data list
     */
		public ArrayList<DataObject> getFavoriteItems() {
			ArrayList<DataObject> favorites = new ArrayList<>();
			for (DataObject item : dataList) {
				if (SimpleFavoriteManager.isFavorite(this, item.getId())) {
					favorites.add(item);
				}
			}
			return favorites;
		}
		
		/**
     * Show favorite count when activity starts
     */
		//    private void showFavoriteCount() {
		//     new Handler().postDelayed(() -> {
		//      int favoriteCount = SimpleFavoriteManager.getAllFavoriteIds(this).size();
		//        if (favoriteCount > 0) {
		//        Toast.makeText(this, 
		///         favoriteCount + " favorite" + (favoriteCount > 1 ? "s" : "") + " loaded", 
		//Toast.LENGTH_SHORT).show();
		//          }
		//       }, 500);
		//  }
		
		/**
     * Clear all favorites and refresh the list
     */
		public void clearAllFavorites() {
			SimpleFavoriteManager.clearAllFavorites(this);
			adapter.notifyDataSetChanged(); // Refresh UI
			Toast.makeText(this, "All favorites cleared", Toast.LENGTH_SHORT).show();
		}
		
		/**
     * Show list of favorite songs
     */
		public void showFavoritesList() {
			ArrayList<DataObject> favorites = getFavoriteItems();
			if (favorites.isEmpty()) {
				Toast.makeText(this, "No favorites yet!", Toast.LENGTH_SHORT).show();
			} else {
				StringBuilder message = new StringBuilder("Your Favorites:\n\n");
				for (int i = 0; i < favorites.size(); i++) {
					DataObject item = favorites.get(i);
					message.append(i + 1).append(". ").append(item.getmText1());
					//   .append(" - ").append(item.getmText2()).append("\n");
				}
				// For longer lists, you might want to use a Dialog instead of Toast
				Toast.makeText(this, message.toString(), Toast.LENGTH_LONG).show();
			}
		}
		
		/**
     * Get favorite count
     */
		public int getFavoriteCount() {
			return SimpleFavoriteManager.getAllFavoriteIds(this).size();
		}
		
		/**
     * Check if there are any favorites
     */
		public boolean hasFavorites() {
			return !SimpleFavoriteManager.getAllFavoriteIds(this).isEmpty();
		}
		
		/**
     * Example method to demonstrate usage
     */
		private void demonstrateUsage() {
			// Example: Check if first item is favorite
			if (!dataList.isEmpty()) {
				DataObject firstItem = dataList.get(0);
				boolean isFavorite = SimpleFavoriteManager.isFavorite(this, firstItem.getId());
				
				// Example: Toggle favorite programmatically
				SimpleFavoriteManager.toggleFavorite(this, firstItem.getId());
			}
			
			// Example: Get all favorite IDs
			ArrayList<String> favoriteIds = new ArrayList<>(
			SimpleFavoriteManager.getAllFavoriteIds(this)
			);
		}
		
		@Override
		protected void onResume() {
			super.onResume();
			// Refresh adapter when returning to activity
			if (adapter != null) {
				adapter.notifyDataSetChanged();
			}
			
		}
		private void loadSongs() {
			try {
				dataList.clear();
				String jsonString = readFromInternalStorage();
				binding.json.setText(jsonString);
				if (jsonString.isEmpty()) {
					// showToast("No songs found");
					return;
				}
				
				JSONArray songsArray = new JSONArray(jsonString);
				
				for (int i = 0; i < songsArray.length(); i++) {
					JSONObject songObject = songsArray.getJSONObject(i);
					String deta= songObject.getString("detail");
					String name="song_"+i;
					String detail = songObject.getString("name");
					dataList.add(new DataObject(name,detail, deta));
				}
				
				adapter.notifyDataSetChanged();
				
			} catch (Exception e) {
				// showToast("Error loading songs: " + e.getMessage());
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
		private void showToast(String message) {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		}
		private void writeToInternalStorage(String content) {
			try {
				FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
				fos.write(content.getBytes());
				fos.close();
			} catch (Exception e) {
				//  showToast("Error saving file: " + e.getMessage());
			}
			
		}
		
		//add new songs and update 
		private void updateSong(String songName, String newDetail) {
			try {
				String jsonString = readFromInternalStorage();
				JSONArray songsArray = new JSONArray(jsonString);
				boolean found = false;
				
				for (int i = 0; i < songsArray.length(); i++) {
					JSONObject song = songsArray.getJSONObject(i);
					if (song.getString("name").equals(songName)) {
						song.put("detail", newDetail);
						found = true;
						dataList.get(i).setmText2(newDetail);
						break;
					}
				}
				
				if (!found) {
					showToast("Song not found: " + songName);
					return;
				}
				
				// Save updated data
				writeToInternalStorage(songsArray.toString());
				adapter.notifyDataSetChanged();
				
				// Clear input fields
				editTextSongName.setText("");
				editTextSongDetail.setText("");
				
				showToast("Song updated successfully!");
				
			} catch (Exception e) {
				showToast("Error updating song: " + e.getMessage());
			}
		}
		
		private void addNewSong(String songName, String songDetail) {
			try {
				String jsonString = readFromInternalStorage();
				JSONArray songsArray;
				
				// Handle empty file case
				if (jsonString.isEmpty()) {
					songsArray = new JSONArray();
				} else {
					songsArray = new JSONArray(jsonString);
				}
				
				// Check if song already exists
				for (int i = 0; i < songsArray.length(); i++) {
					JSONObject song = songsArray.getJSONObject(i);
					if (song.getString("name").equals(songName)) {
						showToast("Song already exists! Use update instead.");
						return;
					}
				}
				
				// Create new song object
				JSONObject newSong = new JSONObject();
				newSong.put("name", songName);
				newSong.put("detail", songDetail);
				
				// Add to array
				songsArray.put(newSong);
				
				// Add to local list
				dataList.add(new DataObject(songName,songDetail, songDetail));
				
				// Save to file
				writeToInternalStorage(songsArray.toString());
				adapter.notifyDataSetChanged();
				
				// Clear input fields
				editTextSongName.setText("");
				editTextSongDetail.setText("");
				
				showToast("Song added successfully!");
				
			} catch (Exception e) {
				showToast("Error adding song: " + e.getMessage());
			}
			
			
			
			
		}
		
		
		public void _block() {
		}
		private SettDialogFragmentActivity SettDialogFragmentActivityN;
		private FragmentManager SettDialogFragmentActivityFM;
		public void test_SettDialogFragmentActivity () {
		}
		
	}