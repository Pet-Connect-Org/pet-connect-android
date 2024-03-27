package com.example.petconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petconnect.activity.LoginActivity;
import com.example.petconnect.activity.MainActivity;

import com.example.petconnect.manager.UserManager;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.post.CreatePostResponse;
import com.example.petconnect.services.post.GetPostResponse;
import com.example.petconnect.services.post.CreatePostRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_createnewpost extends AppCompatActivity {
    Button btnPost;
    EditText txtStatusPost;
    TextView create_post_username,close_create_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnewpost);
        btnPost = findViewById(R.id.btnPost);
        txtStatusPost = findViewById(R.id.txtstatus_post);
        create_post_username = findViewById(R.id.create_post_username);
        close_create_post = findViewById(R.id.close_create_post);
        close_create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_createnewpost.this,MainActivity.class);
                startActivity(intent);
            }
        });
        UserManager userManager = new UserManager(this);
//        create_post_username.setText(userManager.getUser().getUser().getName());
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploatPost();
            }
        });
    }



    private void uploatPost() {

        String createPost = txtStatusPost.getText().toString();
        String accessToken = (new UserManager(this)).getAccessToken();
        if (accessToken==null){
            Toast.makeText(this, "Please login to continue ",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(activity_createnewpost.this, LoginActivity.class);
            startActivity(intent);
            return;
        }else {
            String authorizationHeader = "Bearer "+ accessToken;
            ApiService.apiService.createPost(authorizationHeader, new CreatePostRequest(createPost)).enqueue(new Callback<CreatePostResponse>() {
                @Override
                public void onResponse(Call<CreatePostResponse> call, Response<CreatePostResponse> response) {
                        if (response.isSuccessful()){
                            CreatePostResponse postResponse = response.body();
                            if(postResponse!=null){
                                Toast.makeText(activity_createnewpost.this,"Post created successfully.",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(activity_createnewpost.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                        else {
                            Toast.makeText(activity_createnewpost.this,"Create post failed.",Toast.LENGTH_LONG).show();
                        }
                }

                @Override
                public void onFailure(Call<CreatePostResponse> call, Throwable t) {
                    Toast.makeText(activity_createnewpost.this, "Failed to create post. Please try again" , Toast.LENGTH_SHORT).show();
                }
            });



        }
    }


}