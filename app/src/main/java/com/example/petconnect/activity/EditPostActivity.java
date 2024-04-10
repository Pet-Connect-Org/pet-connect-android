package com.example.petconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
    private EditText editPost;
    private Button btnEditPost;
    private ExtendedPost originalPost;
    TextView closeeditpost,editpostusername;
    UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        userManager = new UserManager(EditPostActivity.this);

        addControl();
        addEvent();
        editpostusername.setText(userManager.getUser().getName());
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String postString = bundle.getString("datapost");
            Gson gson = new Gson();
            originalPost = gson.fromJson(postString,ExtendedPost.class);
            editPost.setText(originalPost.getContent());
        }
    }

    private void addEvent() {
        btnEditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateContent = editPost.getText().toString();

            }


         });
        closeeditpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnEditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePost();
            }
        });

    }



    private void addControl() {
        editPost = findViewById(R.id.txtstatus_editpost);
        btnEditPost = findViewById(R.id.btnSavePost);
        closeeditpost = findViewById(R.id.close_edit_post);
        editpostusername = findViewById(R.id.create_post_username);
    }


    private void updatePost(){

        String token =userManager.getAccessToken();
        String content =editPost.getText().toString();
        ApiService.apiService.updatepost("Bearer "+token,new UpdatePostRequest(content,originalPost.getId()), originalPost.getUser_id()).enqueue(new Callback<UpdatePostResponse>() {
            @Override
            public void onResponse(Call<UpdatePostResponse> call, Response<UpdatePostResponse> response) {
                if (response.isSuccessful()){
                    UpdatePostResponse updatePostResponse = response.body();
                    if (updatePostResponse!=null){
                        Toast.makeText(EditPostActivity.this,"Update post successfully.",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(EditPostActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    Toast.makeText(EditPostActivity.this,"Not authorized to update this post.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpdatePostResponse> call, Throwable t) {
                Toast.makeText(EditPostActivity.this,"Post not found.",Toast.LENGTH_LONG).show();
            }
        });

    }}