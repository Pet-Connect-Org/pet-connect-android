package com.example.petconnect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
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
import com.example.petconnect.models.LikePost;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.comment.AddCommentRequest;
import com.example.petconnect.services.comment.AddCommentResponse;
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
        ImageButton sendButton, post_comment_button;
        Button updateButton;
        RecyclerView recyclerViewCommentList;
        CustomTextfield commentBox;
        CustomDropdown post_action_dropdown;
        NestedScrollView scrollView;

        private String commentContent;

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
            updateButton = itemView.findViewById(R.id.comment_edit_button);
            sendButton = itemView.findViewById(R.id.comment_send);
            commentBox = itemView.findViewById(R.id.comment_box);
            post_action_dropdown = itemView.findViewById(R.id.post_action_dropdown);
            post_comment_button = itemView.findViewById(R.id.post_comment_button);
            scrollView = itemView.findViewById(R.id.scrollView);

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

            ArrayList<Item> actionsList = new ArrayList<>();
            if (post.getUser_id() == userManager.getUser().getId()) {
                actionsList.add(new Item("delete", "Delete"));
                actionsList.add(new Item("update", "Update"));
            }

            post_action_dropdown.setItems(actionsList);

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
                                    notifyItemChanged(position);
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
                        Toast.makeText(PostListAdapter.this.context, "already like", Toast.LENGTH_SHORT).show();
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
                                //Cập nhật RecyclerView thông qua adapter
                                notifyItemChanged(position);
                                updateCommentRecyclerView(comments);
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

            // Scroll to comment box
            post_comment_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Kiểm tra xem ô commentBox có focus không
                    if (commentBox.hasFocus()) {
                        // Nếu có, chuyển focus đi một nơi khác
                        scrollView.requestFocus();
                    }
                    // vị trí của commentBox
                    int scrollToY = commentBox.getTop();

                    // Reset vị trí cuộn về 0
                    scrollView.scrollTo(0, 0);

                    // scroll xuống
                    scrollView.smoothScrollTo(0, scrollToY);
                    commentBox.requestFocus();
                }
            });
        }
    }
}

