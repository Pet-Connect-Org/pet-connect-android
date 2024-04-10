package com.example.petconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.CustomAvatar;
import com.example.petconnect.CustomDropdown;
import com.example.petconnect.R;
import com.example.petconnect.models.ExtendedFollow;

import java.util.List;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.ViewHolder> {
    private List<ExtendedFollow> followerList;
    private Context context;

    public FollowAdapter(Context context,List<ExtendedFollow> followerList) {
        this.context = context;
        this.followerList = followerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExtendedFollow follower = followerList.get(position);
        holder.follower_name.setText(follower.getUser().getName());
       holder.follower_avatar.setName(follower.getUser().getName());
    }

    @Override
    public int getItemCount() {
        return followerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CustomAvatar follower_avatar;
        TextView follower_name;
        Button follow_button;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            follower_avatar = itemView.findViewById(R.id.follower_avatar);
            follower_name = itemView.findViewById(R.id.follower_name);
            follow_button = itemView.findViewById(R.id.follow_button);
        }
    }
}