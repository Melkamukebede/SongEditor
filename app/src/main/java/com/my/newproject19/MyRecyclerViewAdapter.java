package com.my.newproject19;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {
    
    private ArrayList<DataObject> mDataset;
    private Context context;
    private static MyClickListener myClickListener;
    private static MyLongClickListener myLongClickListener;
    
    // Constructor with context
    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset, Context context) {
        this.mDataset = myDataset;
        this.context = context;
    }
    
    // ViewHolder class
    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView textView1, textView2;
        ImageView fav;
        
        public DataObjectHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            fav = itemView.findViewById(R.id.im);
            
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        
        @Override
        public void onClick(final View v) {
            // Animation
            v.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(80)
                .withEndAction(() -> {
                    v.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(80)
                        .start();
                    
                    if (myClickListener != null) {
                        myClickListener.onItemClick(getAdapterPosition(), v);
                    }
                })
                .start();
        }
        
        @Override
        public boolean onLongClick(View v) {
            if (myLongClickListener != null) {
                return myLongClickListener.onItemLongClick(getAdapterPosition(), v);
            }
            return false;
        }
    }
    
    // Click listener interface
    public interface MyClickListener {
        void onItemClick(int position, View v);
    }
    
    // Long click listener interface
    public interface MyLongClickListener {
        boolean onItemLongClick(int position, View v);
    }
    
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
    
    public void setOnItemLongClickListener(MyLongClickListener myLongClickListener) {
        this.myLongClickListener = myLongClickListener;
    }
    
    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new DataObjectHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull DataObjectHolder holder, int position) {
        DataObject item = mDataset.get(position);
        
        // Set text
        holder.textView1.setText(item.getmText1());
        holder.textView2.setText(1+position);
        
        // Background colors (alternating rows)
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#F8F9FA")); // Light gray
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF")); // White
        }
        
        // Text colors
        holder.textView1.setTextColor(Color.parseColor("#212529"));
        holder.textView2.setTextColor(Color.parseColor("#6C757D"));

        // Favorite functionality
        boolean isFavorite = SimpleFavoriteManager.isFavorite(context, item.getId());
        updateFavoriteIcon(holder.fav, isFavorite);
        
        holder.fav.setOnClickListener(v -> {
            SimpleFavoriteManager.toggleFavorite(context, item.getId());
            boolean newState = SimpleFavoriteManager.isFavorite(context, item.getId());
            updateFavoriteIcon(holder.fav, newState);
            
            Toast.makeText(context, 
                newState ? "Added to favorites" : "Removed from favorites", 
                Toast.LENGTH_SHORT).show();
        });
    }
    
    private void updateFavoriteIcon(ImageView fav, boolean isFavorite) {
        if (isFavorite) {
            fav.setImageResource(R.drawable.icon_ful);
           // fav.setColorFilter(Color.TEAL);
        } else {
            fav.setImageResource(R.drawable.icon_n);
          //  fav.setColorFilter(Color.GRAY);
        }
    }
    
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    
    // Helper methods for data manipulation
    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }
    
    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }
    
    public void updateItem(int index, DataObject newData) {
        mDataset.set(index, newData);
        notifyItemChanged(index);
    }
    
    public void clearAll() {
        mDataset.clear();
        notifyDataSetChanged();
    }
    
    // Get item by position
    public DataObject getItem(int position) {
        if (position >= 0 && position < mDataset.size()) {
            return mDataset.get(position);
        }
        return null;
    }
}