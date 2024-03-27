package com.example.petconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.CustomTimeAgo;
import com.example.petconnect.R;
import com.example.petconnect.activity.MainActivity;
import com.example.petconnect.models.ExtendedComment;
import com.example.petconnect.models.ExtendedPost;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {
    private Context context;
    private List<ExtendedPost> postList;

    public PostListAdapter(Context context, List<ExtendedPost> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        ExtendedPost post = postList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView postUserName;
        TextView postContent;
        TextView postLikeCount;
        TextView postCommentCount;
        TextView postTime;
        RecyclerView recyclerViewCommentList;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postUserName = itemView.findViewById(R.id.post_user_name);
            postContent = itemView.findViewById(R.id.post_content);
            postLikeCount = itemView.findViewById(R.id.post_like_count);
            postCommentCount = itemView.findViewById(R.id.post_comment_count);
            postTime = itemView.findViewById(R.id.post_time);
            recyclerViewCommentList = itemView.findViewById(R.id.recyclerViewCommentList);
        }

        public void bind(ExtendedPost post) {
            int likes = post.getComments().size();
            List<ExtendedComment> comments = post.getComments();

            postUserName.setText(post.getUser().getName());
            postContent.setText(post.getContent());
            postLikeCount.setText(String.valueOf(likes) + " " + (likes > 0 ? "Likes" : "Like"));
            postCommentCount.setText(String.valueOf(comments.size()) + " " + (comments.size() > 0 ? "Comments" : "Comment"));
            postTime.setText(CustomTimeAgo.toTimeAgo((post.getCreated_at().getTime())));

            recyclerViewCommentList.setLayoutManager(new LinearLayoutManager(PostListAdapter.this.context, LinearLayoutManager.VERTICAL, false));
            CommentListAdapter commentListAdapter = new CommentListAdapter(PostListAdapter.this.context, comments);
            recyclerViewCommentList.setAdapter(commentListAdapter);

        }
    }
}
