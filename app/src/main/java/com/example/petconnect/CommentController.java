package com.example.petconnect.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.CustomTextfield;
import com.example.petconnect.R;
import com.example.petconnect.adapter.CommentListAdapter;
import com.example.petconnect.models.ExtendedComment;
import com.example.petconnect.models.Post;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.auth.CommentRequest;
import com.example.petconnect.services.auth.CommentResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddComment extends AppCompatActivity {

    private CustomTextfield commentBox;
    private ImageButton sendButton;
    private RecyclerView recyclerViewCommentList;
    private CommentListAdapter commentAdapter;
    private List<ExtendedComment> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_post);

        commentBox = findViewById(R.id.comment_box);
        sendButton = findViewById(R.id.comment_send);
        recyclerViewCommentList = findViewById(R.id.recyclerViewCommentList);

        commentList = new ArrayList<>();
        commentAdapter = new CommentListAdapter(this, commentList);
        recyclerViewCommentList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCommentList.setAdapter(commentAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentContent = commentBox.getText();
                if (!commentContent.trim().isEmpty()) {
                    // Tạo một đối tượng CommentRequest từ nội dung comment và id của post
                    Post post = new Post();
                    int postId = post.getId();
                    CommentRequest newCommentRequest = new CommentRequest(commentContent, postId);

                    // Gửi yêu cầu tạo mới comment lên server
//                    ApiService.apiService.createComment(newCommentRequest).enqueue(new Callback<CommentResponse>() {
//                        @Override
//                        public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
//                            if (response.isSuccessful()) {
//                                CommentResponse commentResponse = response.body();
//                                ExtendedComment newComment = commentResponse.getComment();
//
//                                // Thêm comment mới vào danh sách comment của RecyclerView
//                                commentList.add(newComment);
//                                commentAdapter.notifyDataSetChanged(); // Cập nhật giao diện hiển thị
//
//                                // Hiển thị thông báo thành công
//                                Toast.makeText(AddComment.this, "Comment created successfully", Toast.LENGTH_SHORT).show();
//                            } else {
//                                // Xử lý trường hợp không thành công khi gửi yêu cầu tạo mới comment
//                                Toast.makeText(AddComment.this, "Create comment failed.", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<CommentResponse> call, Throwable t) {
//                            // Xử lý trường hợp thất bại khi gửi yêu cầu tạo mới comment
//                            Toast.makeText(AddComment.this, "Create comment failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
                }
            }
        });
    }
}
