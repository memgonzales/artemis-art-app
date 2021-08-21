package com.mobdeve.gonzales.lee.ong.artemis;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedViewHolder extends RecyclerView.ViewHolder {
    private CircleImageView civItemFeedProfilePic;
    private TextView tvItemFeedUsername;
    private ImageView ivItemFeedPost;
    private TextView tvItemFeedTitle;
    private TextView tvItemFeedUpvoteCounter;
    private TextView tvItemFeedComments;

    public FeedViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        this.civItemFeedProfilePic = itemView.findViewById(R.id.civ_item_feed_profile_pic);
        this.tvItemFeedUsername = itemView.findViewById(R.id.tv_item_feed_username);
        this.ivItemFeedPost = itemView.findViewById(R.id.iv_item_feed_post);
        this.tvItemFeedTitle = itemView.findViewById(R.id.tv_item_feed_title);
        this.tvItemFeedUpvoteCounter = itemView.findViewById(R.id.tv_item_feed_upvote_counter);
        this.tvItemFeedComments = itemView.findViewById(R.id.tv_item_feed_comments);
    }

    public void setItemFeedProfilePic(int picture) {
        this.civItemFeedProfilePic.setImageResource(picture);
    }

    public void setItemFeedUsername(String name) {
        this.tvItemFeedUsername.setText(name);
    }

    public void setItemFeedPost(int post) {
        this.ivItemFeedPost.setImageResource(post);
    }

    public void setItemFeedTitle(String title) {
        this.tvItemFeedTitle.setText(title);
    }

    public void setTvItemFeedUpvoteCounter(String upvotes) {
        this.tvItemFeedUpvoteCounter.setText(upvotes);
    }

    public void setItemFeedComments(String comments) {
        this.tvItemFeedComments.setText(comments);
    }
}
