package com.example.petconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.CustomAvatar;
import com.example.petconnect.CustomTimeAgo;
import com.example.petconnect.R;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedComment;
import com.example.petconnect.models.ExtendedPost;
import com.example.petconnect.models.LikePost;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.post.LikePostRequest;
import com.example.petconnect.services.post.LikePostResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        //ImageButton postLikeButton = view.findViewById(R.id.post_like_button); // Gán giá trị cho postLikeButton
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        ExtendedPost post = postList.get(position);
        if (post != null) {
            holder.bind(post, position);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView postUserName;
        ImageButton postLikeButton;
        TextView postContent;
        TextView postLikeCount;
        TextView postCommentCount;
        TextView postTime;
        RecyclerView recyclerViewCommentList;
        CustomAvatar post_avatar;
        UserManager userManager;
        private int position;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postLikeButton = itemView.findViewById(R.id.post_like_button);
            postUserName = itemView.findViewById(R.id.post_user_name);
            postContent = itemView.findViewById(R.id.post_content);
            postLikeCount = itemView.findViewById(R.id.post_like_count);
            postCommentCount = itemView.findViewById(R.id.post_comment_count);
            postTime = itemView.findViewById(R.id.post_time);
            recyclerViewCommentList = itemView.findViewById(R.id.recyclerViewCommentList);
            post_avatar = itemView.findViewById(R.id.post_avatar);
        }

        public void bind(ExtendedPost post, int position) {
            userManager = new UserManager(PostListAdapter.this.context);
            this.position = position;
            int likes = post.getLikes().size();
            List<ExtendedComment> comments = post.getComments();
            postUserName.setText(post.getUser().getName());
            postContent.setText(post.getContent());
            postLikeCount.setText(String.valueOf(likes) + " " + (likes > 0 ? "Likes" : "Like"));
            postCommentCount.setText(String.valueOf(comments.size()) + " " + (comments.size() > 0 ? "Comments" : "Comment"));
            postTime.setText(CustomTimeAgo.toTimeAgo((post.getCreated_at().getTime())));
            post_avatar.setName(post.getUser().getName());
            for (LikePost likePost : post.getLikes()) {
                if (likePost.getUser_id() == userManager.getUser().getId()) {
                    postLikeButton.setImageResource(R.drawable.footprint_primary);
                    break;
                }
            }
            recyclerViewCommentList.setLayoutManager(new LinearLayoutManager(PostListAdapter.this.context, LinearLayoutManager.VERTICAL, false));
            CommentListAdapter commentListAdapter = new CommentListAdapter(PostListAdapter.this.context, comments);
            recyclerViewCommentList.setAdapter(commentListAdapter);
            postLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String accessToken = (new UserManager(PostListAdapter.this.context)).getAccessToken();
                    ApiService.apiService.likepost("Bearer " + accessToken, post.getId()).enqueue(new Callback<LikePostResponse>() {
                        @Override
                        public void onResponse(Call<LikePostResponse> call, Response<LikePostResponse> response) {
                            if (response.isSuccessful()) {
                                postLikeButton.setImageResource(R.drawable.footprint_primary);
                                post.getLikes().add(new LikePost());
                                postList.set(position, post);
                                notifyItemChanged(position);
                            } else {
                                if (response.code() == 409) {
                                    Toast.makeText(context.getApplicationContext(), "Already like.", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context.getApplicationContext(), "Failed. Please try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LikePostResponse> call, Throwable t) {
                            Toast.makeText(context.getApplicationContext(), "Failed. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}
