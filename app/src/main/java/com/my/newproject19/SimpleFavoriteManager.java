package com.my.newproject19;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class SimpleFavoriteManager {
    
    private static final String PREFS_NAME = "favorites_prefs";
    private static final String KEY_FAVORITES = "favorite_items";
    
    // Private constructor to prevent instantiation
    private SimpleFavoriteManager() {}
    
    /**
     * Save favorite state for an item
     */
    public static void saveFavoriteState(Context context, String itemId, boolean isFavorite) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> favorites = getFavoritesSet(prefs);
        
        if (isFavorite) {
            favorites.add(itemId);
        } else {
            favorites.remove(itemId);
        }
        
        prefs.edit().putStringSet(KEY_FAVORITES, favorites).apply();
    }
    
    /**
     * Check if an item is favorite
     */
    public static boolean isFavorite(Context context, String itemId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> favorites = getFavoritesSet(prefs);
        return favorites.contains(itemId);
    }
    
    /**
     * Toggle favorite state
     */
    public static void toggleFavorite(Context context, String itemId) {
        boolean currentState = isFavorite(context, itemId);
        saveFavoriteState(context, itemId, !currentState);
    }
    
    /**
     * Clear all favorites
     */
    public static void clearAllFavorites(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_FAVORITES).apply();
    }
    
    /**
     * Get all favorite IDs
     */
    public static Set<String> getAllFavoriteIds(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return getFavoritesSet(prefs);
    }
    
    private static Set<String> getFavoritesSet(SharedPreferences prefs) {
        return new HashSet<>(prefs.getStringSet(KEY_FAVORITES, new HashSet<>()));
    }
}