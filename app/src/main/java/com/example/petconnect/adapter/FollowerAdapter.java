package com.example.petconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.CustomAvatar;
import com.example.petconnect.CustomDropdown;
import com.example.petconnect.R;
import com.example.petconnect.models.Follow;

import java.util.List;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.ViewHolder> {
    private List<Follow> followerList;
    private Context context;

    public FollowerAdapter(Context context,List<Follow> followerList) {
        this.context = context;
        this.followerList = followerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follower, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Follow follower = followerList.get(position);
        // Set follower's name
        holder.follower_name.setText(follower.getUser_id());
        // Handle button click here
        holder.threeDot.setOnClickListener(v -> {
            // Handle button click here
        });
    }

    @Override
    public int getItemCount() {
        return followerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CustomAvatar follower_avatar;
        TextView follower_name;
        ImageButton threeDot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            follower_avatar = itemView.findViewById(R.id.follower_avatar);
            follower_name = itemView.findViewById(R.id.follower_name);
            threeDot = itemView.findViewById(R.id.three_dot_btn);
        }
    }
}
