package com.example.petconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petconnect.R;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedPost;
import com.example.petconnect.models.Post;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.post.UpdatePostRequest;
import com.example.petconnect.services.post.UpdatePostResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPostActivity extends AppCompatActivity {
    TextView closeeditpost, editpostusername;
    UserManager userManager;
    private EditText editPost;
    private Button btnEditPost;
    private ExtendedPost originalPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        userManager = new UserManager(EditPostActivity.this);

        addControl();
        addEvent();
        editpostusername.setText(userManager.getUser().getName());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String postString = bundle.getString("datapost");
            Gson gson = new Gson();
            originalPost = gson.fromJson(postString, ExtendedPost.class);
            editPost.setText(originalPost.getContent());
        }
    }

    private void addEvent() {
        btnEditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePost();
            }
        });

        closeeditpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControl() {
        editPost = findViewById(R.id.txtstatus_editpost);
        btnEditPost = findViewById(R.id.btnSavePost);
        closeeditpost = findViewById(R.id.close_edit_post);
        editpostusername = findViewById(R.id.create_post_username);
    }


    private void updatePost() {
        // Lấy nội dung mới từ EditText
        String updatedContent = editPost.getText().toString();
        // Kiểm tra nếu nội dung mới không trống và originalPost không null
        if (!updatedContent.trim().isEmpty() && originalPost != null) {
            // Cập nhật nội dung của originalPost
            originalPost.setContent(updatedContent);
            // Lấy token của người dùng hiện tại
            String token = userManager.getAccessToken();

            // Gọi API để cập nhật bài đăng
            ApiService.apiService.updatepost("Bearer " + token, new UpdatePostRequest(updatedContent), originalPost.getId()).enqueue(new Callback<UpdatePostResponse>() {
                @Override
                public void onResponse(Call<UpdatePostResponse> call, Response<UpdatePostResponse> response) {
                    if (response.isSuccessful()) {
                        UpdatePostResponse updateResponse = response.body();
                        // Lấy dữ liệu post được cập nhật từ response
                        Post updatedPost = updateResponse.getData();
                        // Hiển thị thông báo cập nhật thành công
                        Toast.makeText(EditPostActivity.this, "Update post successfully.", Toast.LENGTH_LONG).show();
                        // Cập nhật EditText với nội dung mới
                        editPost.setText(updatedContent);
                        // Cập nhật thành công
                        finish();

                    } else {
                        Toast.makeText(EditPostActivity.this, "Not authorized to update this post.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdatePostResponse> call, Throwable t) {
                    Toast.makeText(EditPostActivity.this, "Update post failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            // Nếu nội dung mới trống hoặc originalPost null, hiển thị thông báo tương ứng
            Toast.makeText(EditPostActivity.this, "Post content cannot be empty or post not found.", Toast.LENGTH_LONG).show();
        }
    }
}