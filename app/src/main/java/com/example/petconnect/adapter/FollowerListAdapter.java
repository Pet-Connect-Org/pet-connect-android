package com.example.petconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.CustomAvatar;
import com.example.petconnect.R;
import com.example.petconnect.models.Follow;

import java.util.List;

public class FollowerListAdapter extends RecyclerView.Adapter<FollowerListAdapter.FollowViewHolder> {
    private Context context;
    private List<Follow> followerList;

    public FollowerListAdapter(Context context, List<Follow> followerList) {
        this.context = context;
        this.followerList = followerList;
    }

    @NonNull
    @Override
    public FollowerListAdapter.FollowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fragment_follower, parent, false);
        return new FollowerListAdapter.FollowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerListAdapter.FollowViewHolder holder, int position) {
        Follow follow = followerList.get(position);
        holder.bind(follow);
    }

    @Override
    public int getItemCount() {
        return followerList.size();
    }
    public class FollowViewHolder extends RecyclerView.ViewHolder {
        TextView followerName;
        ImageButton threeDotBtn;
        CustomAvatar followerAvatar;

        public FollowViewHolder(@NonNull View itemView) {
            super(itemView);
            followerName = itemView.findViewById(R.id.follower_name);
            followerAvatar = itemView.findViewById(R.id.follower_avatar);
            threeDotBtn = itemView.findViewById(R.id.three_dot_btn);

        }
        public void bind(Follow follow) {
            followerName.setText(follow.getUser_id());
//            followerAvatar.setVisibility(View.VISIBLE);
//            threeDotBtn.setVisibility(View.VISIBLE);
        }
    }
}
