package com.my.newproject19;

import android.content.Context;

public class DataObject {
    private String id; // ← ADD UNIQUE ID FIELD
    private String mText1;
    private String mText2;
    private boolean isImportant;
    
    // Constructor with ID
    public DataObject(String id, String text1, String text2) {
        this.id = id;
        this.mText1 = text1;
        this.mText2 = text2;
        this.isImportant = false;
    }
    
    public DataObject(String id, String text1, String text2, boolean isImportant) {
        this.id = id;
        this.mText1 = text1;
        this.mText2 = text2;
        this.isImportant = isImportant;
    }
    
    // Getters
    public String getId() { return id; } // ← ADD GETTER FOR ID
    public String getmText1() { return mText1; }
    public String getmText2() { return mText2; }
    public boolean isImportant() { return isImportant; }
    
    // Setters
    public void setId(String id) { this.id = id; } // ← ADD SETTER FOR ID
    public void setmText1(String text1) { this.mText1 = text1; }
    public void setmText2(String text2) { this.mText2 = text2; }
    public void setImportant(boolean important) { isImportant = important; }
    
    /**
     * Check if this item is favorite (uses SharedPreferences)
     */
    public boolean isFavorite(Context context) {
        return SimpleFavoriteManager.isFavorite(context, this.id);
    }
    
    /**
     * Set favorite state (uses SharedPreferences)
     */
    public void setFavorite(Context context, boolean favorite) {
        SimpleFavoriteManager.saveFavoriteState(context, this.id, favorite);
    }
    
    /**
     * Toggle favorite state
     */
    public void toggleFavorite(Context context) {
        SimpleFavoriteManager.toggleFavorite(context, this.id);
    }
}