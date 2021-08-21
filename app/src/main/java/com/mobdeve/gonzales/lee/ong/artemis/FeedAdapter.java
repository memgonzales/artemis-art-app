package com.mobdeve.gonzales.lee.ong.artemis;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {
    private ArrayList<Post> dataPosts;


    @NonNull
    @NotNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FeedViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
