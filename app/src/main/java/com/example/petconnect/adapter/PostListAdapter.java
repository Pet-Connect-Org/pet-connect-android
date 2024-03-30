package com.example.petconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.CustomAvatar;
import com.example.petconnect.CustomDropdown;
import com.example.petconnect.CustomTextfield;
import com.example.petconnect.CustomTimeAgo;
import com.example.petconnect.Item;
import com.example.petconnect.R;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedComment;
import com.example.petconnect.models.ExtendedPost;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.comment.AddCommentRequest;
import com.example.petconnect.services.comment.AddCommentResponse;

import com.example.petconnect.models.LikePost;
import com.example.petconnect.services.post.LikePostResponse;
import com.example.petconnect.services.post.UnlikePostResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        TextView postTime, postLikeText;
        CustomAvatar post_avatar;
        UserManager userManager;
        boolean isUserLike = false;
        ImageButton sendButton;

        CustomDropdown post_sort_comment;
        LinearLayout commentBoxHover;
        Button updateButton;
        RecyclerView recyclerViewCommentList;
        CustomTextfield commentBox;
        CustomDropdown post_action_dropdown;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            userManager = new UserManager(PostListAdapter.this.context);
            postLikeButton = itemView.findViewById(R.id.post_like_button);
            postUserName = itemView.findViewById(R.id.post_user_name);
            postContent = itemView.findViewById(R.id.post_content);
            postLikeCount = itemView.findViewById(R.id.post_like_count);
            postCommentCount = itemView.findViewById(R.id.post_comment_count);
            postTime = itemView.findViewById(R.id.post_time);
            recyclerViewCommentList = itemView.findViewById(R.id.recyclerViewCommentList);
            post_avatar = itemView.findViewById(R.id.post_avatar);
            postLikeText = itemView.findViewById(R.id.post_like_text);
            commentBoxHover = itemView.findViewById(R.id.comment_box_hover);
            updateButton = itemView.findViewById(R.id.comment_edit_button);
            sendButton = itemView.findViewById(R.id.comment_send);
            commentBox = itemView.findViewById(R.id.comment_box);
            post_action_dropdown = itemView.findViewById(R.id.post_action_dropdown);
            post_sort_comment = itemView.findViewById(R.id.post_sort_comment);
        }

        private void updateCommentRecyclerView(List<ExtendedComment> comments) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerViewCommentList.setLayoutManager(layoutManager);
            CommentListAdapter commentListAdapter = new CommentListAdapter(context, comments);
            recyclerViewCommentList.setAdapter(commentListAdapter);
        }

        public void bind(ExtendedPost post, int position) {
            int likes = post.getLikes().size();
            List<ExtendedComment> comments = post.getComments();
            postUserName.setText(post.getUser().getName());
            postContent.setText(post.getContent());
            postLikeCount.setText(String.valueOf(likes) + " " + (likes > 1 ? "Likes" : "Like"));
            postCommentCount.setText(String.valueOf(comments.size()) + " " + (comments.size() > 0 ? "Comments" : "Comment"));
            postTime.setText(CustomTimeAgo.toTimeAgo((post.getCreated_at().getTime())));

            updateCommentRecyclerView(comments);

            post_action_dropdown.customizeDropdown(android.R.color.transparent, R.drawable.more, false);
            post_sort_comment.customizeDropdown(android.R.color.transparent, R.drawable.sort, false);

            ArrayList<Item> sortOptionList = new ArrayList<>();
            sortOptionList.add(new Item("desc", "Oldest first"));
            sortOptionList.add(new Item("asc", "Newest first"));

            ArrayList<Item> actionsList = new ArrayList<>();
            if (post.getUser_id() == userManager.getUser().getId()) {
                actionsList.add(new Item("delete", "Delete"));
                actionsList.add(new Item("update", "Update"));
            }

            post_action_dropdown.setItems(actionsList);
            post_sort_comment.setItems(sortOptionList);
            post_sort_comment.setOnItemSelectedListener(new CustomDropdown.OnItemSelectedListener() {
                @Override
                public void onItemSelected(String key) {
                    ArrayList<ExtendedComment> sortedComments = post.getComments();

                    if (key == "desc") {
                        Collections.sort(sortedComments, new Comparator<ExtendedComment>() {
                            @Override
                            public int compare(ExtendedComment comment1, ExtendedComment comment2) {
                                return comment1.getCreated_at().compareTo(comment2.getCreated_at());
                            }
                        });
                        post.setComments(sortedComments);
                        updateCommentRecyclerView(sortedComments);
                    } else if (key == "asc") {
                        Collections.sort(sortedComments, new Comparator<ExtendedComment>() {
                            @Override
                            public int compare(ExtendedComment comment1, ExtendedComment comment2) {
                                return comment2.getCreated_at().compareTo(comment1.getCreated_at());
                            }
                        });
                        post.setComments(sortedComments);
                        updateCommentRecyclerView(sortedComments);
                    }
                }
            });

            post_action_dropdown.setOnItemSelectedListener(new CustomDropdown.OnItemSelectedListener() {
                @Override
                public void onItemSelected(String key) {
                    // Xử lý sự kiện nhấn delete
                    if (key == "delete") {
                        Toast.makeText(PostListAdapter.this.context, "Selected item: " + key, Toast.LENGTH_SHORT).show();
                    }
                    // Xử lý sự kiện nhấn update
                    if (key == "update") {
                        Toast.makeText(PostListAdapter.this.context, "Selected item: " + key, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // kiểm tra trong list likes có tồn tại like của người dùng
            for (LikePost likePost : post.getLikes()) {
                if (likePost.getUser_id() == userManager.getUser().getId()) {
                    postLikeText.setTextColor(ContextCompat.getColor(context, R.color.primaryMain));
                    postLikeButton.setImageResource(R.drawable.footprint_primary);
                    this.isUserLike = true;
                    break;
                }
            }

            postLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String accessToken = userManager.getAccessToken();

                    // kiểm tra nếu người dùng chưa like mới cho like
                    if (!isUserLike) {
                        ApiService.apiService.likepost("Bearer " + accessToken, post.getId()).enqueue(new Callback<LikePostResponse>() {
                            @Override
                            public void onResponse(Call<LikePostResponse> call, Response<LikePostResponse> response) {
                                if (response.isSuccessful()) {
                                    postLikeButton.setImageResource(R.drawable.footprint_primary);
                                    postLikeText.setTextColor(ContextCompat.getColor(context, R.color.primaryMain));
                                    post.getLikes().add(response.body().getData());
                                    isUserLike = true;
                                } else {
                                    Toast.makeText(context.getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LikePostResponse> call, Throwable t) {
                                Toast.makeText(context.getApplicationContext(), "Failed. Please try again", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        ApiService.apiService.unlikepost("Bearer " + accessToken, post.getId()).enqueue(new Callback<UnlikePostResponse>() {
                            @Override
                            public void onResponse(Call<UnlikePostResponse> call, Response<UnlikePostResponse> response) {
                                if (response.isSuccessful()) {
                                    postLikeButton.setImageResource(R.drawable.footprint);
                                    postLikeText.setTextColor(ContextCompat.getColor(context, R.color.defaultTextColor));
                                    post.getLikes().removeIf(like -> like.getUser_id() == userManager.getUser().getId());
                                    isUserLike = false;
                                } else {
                                    Toast.makeText(context.getApplicationContext(), "Unlike failed", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UnlikePostResponse> call, Throwable t) {
                                Toast.makeText(context.getApplicationContext(), "Failed. Please try again", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            });
            // Add comment
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String commentContent = commentBox.getText().toString();
                    String token = userManager.getAccessToken();
                    // Gửi yêu cầu tạo mới comment đến server với content và post_id
                    ApiService.apiService.createComment("Bearer " + token, new AddCommentRequest(commentContent, post.getId())).enqueue(new Callback<AddCommentResponse>() {
                        @Override
                        public void onResponse(Call<AddCommentResponse> call, Response<AddCommentResponse> response) {
                            if (response.isSuccessful()) {
                                // Thêm comment mới vào danh sách comments của post
                                comments.add(response.body().getData());
//                               Cập nhật RecyclerView thông qua adapter
                                notifyItemChanged(position);
                                updateCommentRecyclerView(comments);
//
                                // Hiển thị thông báo
                                Toast.makeText(context, "Comment Added", Toast.LENGTH_SHORT).show();
                            } else {
                                // Xử lý khi gửi yêu cầu tạo mới comment thất bại
                                Toast.makeText(context, "Comment Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddCommentResponse> call, Throwable t) {
                            // Xử lý khi gửi yêu cầu tạo mới comment thất bại
                            Toast.makeText(context, "Create comment failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

    }
}

