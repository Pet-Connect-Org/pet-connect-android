package com.example.petconnect.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.CustomAvatar;
import com.example.petconnect.CustomDropdown;
import com.example.petconnect.CustomTextfield;
import com.example.petconnect.CustomTimeAgo;
import com.example.petconnect.Item;
import com.example.petconnect.R;
import com.example.petconnect.activity.EditPostActivity;
import com.example.petconnect.activity.ProfileActivity;

import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedComment;
import com.example.petconnect.models.ExtendedPost;
import com.example.petconnect.models.LikePost;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.comment.AddCommentRequest;
import com.example.petconnect.services.comment.AddCommentResponse;
import com.example.petconnect.services.post.DeletePostResponse;
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
        ImageButton sendButton, post_comment_button;
        Button updateButton;
        RecyclerView recyclerViewCommentList;
        CustomTextfield commentBox;
        CustomDropdown post_action_dropdown, post_sort_comment;
        NestedScrollView scrollView;
        LinearLayout parentLayout;


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
            post_sort_comment = itemView.findViewById(R.id.post_sort_comment);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }

        private void updateCommentRecyclerView(List<ExtendedComment> comments) {
            CommentListAdapter commentListAdapter = new CommentListAdapter(context, comments);
            recyclerViewCommentList.setAdapter(commentListAdapter);
        }



        @SuppressLint("ClickableViewAccessibility")
        public void bind(ExtendedPost post, int position) {
            int likes = post.getLikes().size();
            List<ExtendedComment> comments = post.getComments();
            postUserName.setText(post.getUser().getName());
            post_avatar.setName(post.getUser().getName());
            postContent.setText(post.getContent());
            postLikeCount.setText(String.valueOf(likes) + " " + (likes > 1 ? "Likes" : "Like"));
            postCommentCount.setText(String.valueOf(comments.size()) + " " + (comments.size() > 1 ? "Comments" : "Comment"));
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

            postUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myintent = new Intent(PostListAdapter.this.context, ProfileActivity.class);
                    myintent.putExtra("user_id", String.valueOf(post.getUser_id()));
                    PostListAdapter.this.context.startActivity(myintent);
                }
            });

            post_action_dropdown.setOnItemSelectedListener(new CustomDropdown.OnItemSelectedListener() {
                @Override
                public void onItemSelected(String key) {
                    // Xử lý sự kiện nhấn delete
                    if ("delete".equals(key)) {
                        String accessToken = userManager.getAccessToken();
                        ApiService.apiService.deletePost("Bearer "+ accessToken, post.getId()).enqueue(new Callback<DeletePostResponse>(){
                            @Override
                            public void onResponse(Call<DeletePostResponse> call, Response<DeletePostResponse> response) {
                                if(response.isSuccessful()){
                                    postList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, postList.size()); // Cập nhật số lượng item trong RecyclerView
                                    Toast.makeText(PostListAdapter.this.context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(PostListAdapter.this.context, "Delete failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<DeletePostResponse> call, Throwable t) {
                                Toast.makeText(PostListAdapter.this.context, "Delete failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                    // Xử lý sự kiện nhấn update
                    if ("update".equals(key)) {
                        Toast.makeText(PostListAdapter.this.context, "Selected item: " + key, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context.getApplicationContext(), EditPostActivity.class);

                        intent.putExtra("datapost",post.toString());
                        context.startActivity(intent);
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
                                    notifyDataSetChanged();
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
                                    notifyDataSetChanged();
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
                                updateCommentRecyclerView(comments);
                                commentBox.setText("");
                                // Hiển thị thông báo
                                Toast.makeText(context, "Comment Added", Toast.LENGTH_SHORT).show();
                            } else {
                                // Xử lý khi gửi yêu cầu tạo mới comment thất bại
                                Toast.makeText(context, "Comment Failed", Toast.LENGTH_SHORT).show();
                            }

                            commentBox.clearFocus();
                        }

                        @Override
                        public void onFailure(Call<AddCommentResponse> call, Throwable t) {
                            // Xử lý khi gửi yêu cầu tạo mới comment thất bại
                            Toast.makeText(context, "Create comment failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                            // Xóa focus khỏi commentBox
                            commentBox.clearFocus();
                        }
                    });
                    // Ẩn bàn phím ảo
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            });
            // nhấn enter gửi comment
            commentBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                        // Gọi sự kiện onClick của nút gửi comment
                        sendButton.performClick();
                        return true; // Trả về true để chỉ ra rằng sự kiện đã được xử lý
                    }
                    return false;
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

            parentLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Lấy Context từ parentLayout
                    Context context = v.getContext();

                    // Lấy tọa độ chạm
                    float touchX = event.getRawX();
                    float touchY = event.getRawY();

                    // Lấy tọa độ của commentBox trong cửa sổ của ứng dụng
                    int[] location = new int[2];
                    commentBox.getLocationInWindow(location);
                    int commentBoxX = location[0];
                    int commentBoxY = location[1];

                    // Kiểm tra xem vị trí chạm có nằm trong commentBox hay không
                    if (touchX < commentBoxX || touchX > commentBoxX + commentBox.getWidth() ||
                            touchY < commentBoxY || touchY > commentBoxY + commentBox.getHeight()) {
                        commentBox.clearFocus();

                        // Ẩn bàn phím ảo
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(commentBox.getWindowToken(), 0);
                    }
                    return false;
                }
            });

            // khi ấn ra ngoài commentBox thì clear focus, tắt bàn phím ảo
            recyclerViewCommentList.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Lấy Context từ RecyclerView
                    Context context = v.getContext();

                    // Lấy tọa độ chạm
                    float touchX = event.getRawX();
                    float touchY = event.getRawY();

                    // Lấy tọa độ của commentBox trong cửa sổ của ứng dụng
                    int[] location = new int[2];
                    commentBox.getLocationInWindow(location);
                    int commentBoxX = location[0];
                    int commentBoxY = location[1];

                    // Kiểm tra xem vị trí chạm có nằm trong commentBox hay không
                    if (touchX < commentBoxX || touchX > commentBoxX + commentBox.getWidth() ||
                            touchY < commentBoxY || touchY > commentBoxY + commentBox.getHeight()) {
                        // Nếu không, xóa focus khỏi commentBox để con trỏ chuột biến mất
                        commentBox.clearFocus();

                        // Ẩn bàn phím ảo
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(commentBox.getWindowToken(), 0);
                    }

                    // Trả về false để cho phép các sự kiện khác (nếu có)
                    return false;
                }
            });
        }

    }


}


