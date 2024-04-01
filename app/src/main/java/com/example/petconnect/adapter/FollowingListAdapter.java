package com.example.petconnect.adapter;

import android.content.Context;
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

public class FollowingListAdapter extends RecyclerView.Adapter<FollowingListAdapter.FollowingViewHolder> {
    private Context context;
    private List<Follow> followingList;

    public FollowingListAdapter(Context context, List<Follow> followingList) {
        this.context = context;
        this.followingList = followingList;
    }

    @NonNull
    @Override
    public FollowingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fragment_following, parent, false);
        return new FollowingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingViewHolder holder, int position) {
        Follow follow = followingList.get(position);
        holder.bind(follow);
    }

    @Override
    public int getItemCount() {
        return followingList.size();
    }

    public class FollowingViewHolder extends RecyclerView.ViewHolder {
        TextView followingUsername;
        CustomAvatar followingAvatar;
        Button followingButton;

        public FollowingViewHolder(@NonNull View itemView) {
            super(itemView);
            followingUsername = itemView.findViewById(R.id.sidebar_username);
            followingAvatar = itemView.findViewById(R.id.following_ava);
            followingButton = itemView.findViewById(R.id.following_btn);
        }

        public void bind(Follow follow) {
            followingUsername.setText(follow.getUser_id());
//             followingAvatar.setVisibility(View.VISIBLE);
             followingButton.setVisibility(View.VISIBLE);
        }
    }
}
