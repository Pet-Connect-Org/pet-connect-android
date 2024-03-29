package com.example.petconnect.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.CustomAvatar;
import com.example.petconnect.CustomTimeAgo;
import com.example.petconnect.R;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedComment;

import java.util.List;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder> {
    private Context context;
    private List<ExtendedComment> commentList;

    public CommentListAdapter(Context context, List<ExtendedComment> commentList) {
        this.context = context;
        this.commentList = commentList;

    }


    @NonNull
    @Override
    public CommentListAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentListAdapter.CommentViewHolder holder, int position) {
        ExtendedComment comment = commentList.get(position);

        holder.bind(comment);


    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView comment_author_name, comment_content, comment_time;
        CustomAvatar comment_author_avatar;
        UserManager userManager;

        Button comment_delete_button, comment_edit_button, comment_like_button;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            comment_author_name = itemView.findViewById(R.id.comment_author_name);
            comment_content = itemView.findViewById(R.id.comment_content);
            comment_author_avatar = itemView.findViewById(R.id.comment_author_avatar);
            comment_time = itemView.findViewById(R.id.comment_time);

            comment_delete_button = itemView.findViewById(R.id.comment_delete_button);
            comment_edit_button = itemView.findViewById(R.id.comment_edit_button);
        }

        public void bind(ExtendedComment comment) {
            comment_author_name.setText(comment.getUser().getName());
            comment_content.setText(comment.getContent());
            comment_author_avatar.setName(comment.getUser().getName());
            userManager = new UserManager(CommentListAdapter.this.context);
            comment_time.setText(CustomTimeAgo.toTimeAgo((comment.getCreated_at().getTime())));
            if (comment.getUser().getId() == userManager.getUser().getId()) {
                comment_delete_button.setVisibility(View.VISIBLE);
                comment_edit_button.setVisibility(View.VISIBLE);
            }
        }


    }
}
