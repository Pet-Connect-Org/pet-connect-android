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

import com.example.petconnect.CustomTextfield;
import com.example.petconnect.CustomTimeAgo;
import com.example.petconnect.R;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedComment;
import com.example.petconnect.models.ExtendedPost;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.comment.AddCommentRequest;
import com.example.petconnect.services.comment.AddCommentResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {
    private Context context;
    private List<ExtendedPost> postList;
//    private User currentUser;

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
            holder.bind(post);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

//    public void setCurrentUser(User currentUser) {
//        this.currentUser = currentUser;
//    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView postUserName;
        TextView postContent;
        TextView postLikeCount;
        TextView postCommentCount;
        TextView postTime;
        CustomTextfield commentBox;
        ImageButton sendButton;
        RecyclerView recyclerViewCommentList;


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postUserName = itemView.findViewById(R.id.post_user_name);
            postContent = itemView.findViewById(R.id.post_content);
            postLikeCount = itemView.findViewById(R.id.post_like_count);
            postCommentCount = itemView.findViewById(R.id.post_comment_count);
            postTime = itemView.findViewById(R.id.post_time);
            commentBox = itemView.findViewById(R.id.comment_box);
            sendButton = itemView.findViewById(R.id.comment_send);
            recyclerViewCommentList = itemView.findViewById(R.id.recyclerViewCommentList); // RecyclerView để hiển thị danh sách comment
        }

        public void bind(ExtendedPost post) {
            int likes = post.getLikes().size();
            List<ExtendedComment> comments = post.getComments();

            postUserName.setText(post.getUser().getName());
            postContent.setText(post.getContent());
            postLikeCount.setText(String.valueOf(likes) + " " + (likes > 0 ? "Likes" : "Like"));
            postCommentCount.setText(String.valueOf(comments.size()) + " " + (comments.size() > 0 ? "Comments" : "Comment"));
            postTime.setText(CustomTimeAgo.toTimeAgo((post.getCreated_at().getTime())));

            // Xử lý sự kiện khi người dùng nhấn nút sendButton để thêm comment
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String commentContent = commentBox.getText().toString();
                    String token = (new UserManager(PostListAdapter.this.context)).getAccessToken();
                    // Gửi yêu cầu tạo mới comment đến server với content và post_id
                    ApiService.apiService.createComment("Bearer" + token, new AddCommentRequest(commentContent, 16)).enqueue(new Callback<AddCommentResponse>() {
                        @Override
                        public void onResponse(Call<AddCommentResponse> call, Response<AddCommentResponse> response) {
                            if (response.isSuccessful()) {
                                // Thêm comment mới vào danh sách comments của post
//                                comments.add(response.body().getData());
//
//                                // Cập nhật RecyclerView thông qua adapter
//                                notifyDataSetChanged();

                                // Hiển thị thông báo
                                Toast.makeText(context, response.body().getData().getUser().getName(), Toast.LENGTH_SHORT).show();
                            } else {
                                // Xử lý khi gửi yêu cầu tạo mới comment thất bại
                                Toast.makeText(context, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
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

            // Phương thức cập nhật RecyclerView
            recyclerViewCommentList.setLayoutManager(new LinearLayoutManager(context));
            CommentListAdapter commentListAdapter = new CommentListAdapter(context, comments);
            recyclerViewCommentList.setAdapter(commentListAdapter);
        }
    }
}

//        public void bind(ExtendedPost post) {
//            int likes = post.getLikes().size();
//            List<ExtendedComment> comments = post.getComments();
//
//            postUserName.setText(post.getUser().getName());
//            postContent.setText(post.getContent());
//            postLikeCount.setText(String.valueOf(likes) + " " + (likes > 0 ? "Likes" : "Like"));
//            postCommentCount.setText(String.valueOf(comments.size()) + " " + (comments.size() > 0 ? "Comments" : "Comment"));
//            postTime.setText(CustomTimeAgo.toTimeAgo((post.getCreated_at().getTime())));
//
//            // Xử lý sự kiện khi nhấn nút sendButton
//            sendButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String commentContent = commentBox.getText().toString();
//
//                    // Tạo một instance của ExtendedComment từ dữ liệu nhập vào
//                    ExtendedComment newComment = new ExtendedComment();
//                    newComment.setContent(commentContent);
//                    newComment.setCreated_at(new Date()); // Set thời gian tạo mới comment
//                    newComment.setUser(currentUser); // Set thông tin của user hiện tại (nếu đã đăng nhập)
//
//                    // Thêm comment mới vào danh sách comments của post
//                    comments.add(newComment);
//
//                    // Cập nhật RecyclerView thông qua adapter
//                    notifyDataSetChanged();
//
//
//                    // Hiển thị thông báo
//                    Toast.makeText(context, "Comment added successfully", Toast.LENGTH_SHORT).show();
//                }
//            });
//            sendButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String commentContent = commentBox.getText().toString();
//
//                    // Gửi yêu cầu tạo mới comment đến server với content và post_id
//                    ApiService.apiService.createComment(new AddCommentRequest(commentContent, post.getId())).enqueue(new Callback<AddCommentResponse>() {
//                        @Override
//                        public void onResponse(Call<AddCommentResponse> call, Response<AddCommentResponse> response) {
//                            if (response.isSuccessful()) {
//            String commentContent = commentBox.getText().toString();
//
//                    // Tạo một instance của ExtendedComment từ dữ liệu phản hồi
//                    ExtendedComment newComment = new ExtendedComment(1, 2, 3, commentContent, new Date(), new Date(), null,  null );
//
//                    // Thêm comment mới vào danh sách comments
//                    comments.add(newComment);
//
//                    // Cập nhật RecyclerView thông qua adapter
//                    notifyDataSetChanged();
//
//                    // Hiển thị thông báo
//                    Toast.makeText(context, "Comment added successfully", Toast.LENGTH_SHORT).show();
//                            } else {
//                                // Xử lý khi gửi yêu cầu tạo mới comment thất bại
//                                Toast.makeText(context, "Create comment failed.", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<AddCommentResponse> call, Throwable t) {
//                            // Xử lý khi gửi yêu cầu tạo mới comment thất bại
//                            Toast.makeText(context, "Create comment failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            });

//            // Phương thức cập nhật RecyclerView
//            recyclerViewCommentList.setLayoutManager(new LinearLayoutManager(context));
//            CommentListAdapter commentListAdapter = new CommentListAdapter(context, comments,currentUser);
//            recyclerViewCommentList.setAdapter(commentListAdapter);
//        }
//    }
//}