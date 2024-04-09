package com.example.petconnect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.CustomAvatar;
import com.example.petconnect.R;
import com.example.petconnect.models.Follow;

import java.util.List;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder> {
    private List<Follow> followingList;

    public FollowingAdapter(List<Follow> followingList) {
        this.followingList = followingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_following, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Follow following = followingList.get(position);
        // Set following's name
        holder.following_name.setText(following.getFollowing_user_id());
        // Handle button click here
        holder.following_button.setOnClickListener(v -> {
            // Handle button click here
        });
    }

    @Override
    public int getItemCount() {
        return followingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CustomAvatar following_avatar;
        TextView following_name;
        Button following_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            following_avatar = itemView.findViewById(R.id.following_avatar);
            following_name = itemView.findViewById(R.id.following_name);
            following_button = itemView.findViewById(R.id.following_button);
        }
    }
}
