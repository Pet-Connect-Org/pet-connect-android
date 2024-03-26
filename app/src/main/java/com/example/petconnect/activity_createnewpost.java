package com.example.petconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petconnect.activity.LoginActivity;
import com.example.petconnect.activity.MainActivity;
import com.example.petconnect.manager.AccessTokenManager;
import com.example.petconnect.models.ExtendedAccount;
import com.example.petconnect.models.ExtendedPost;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.auth.LoginRequest;
import com.example.petconnect.services.auth.LoginResponse;
import com.example.petconnect.services.post.GetPostResponse;
import com.example.petconnect.services.post.PostRequest;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_createnewpost extends AppCompatActivity {
    Button btnPost;
    EditText txtStatusPost;
    AccessTokenManager accessTokenManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnewpost);
        btnPost = findViewById(R.id.btnPost);
        txtStatusPost = findViewById(R.id.txtstatus_post);

        accessTokenManager = new AccessTokenManager(this);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploatPost();
            }
        });
    }



    private void uploatPost() {
        String accessToken = accessTokenManager.getAccessToken();
        String createPost = txtStatusPost.getText().toString();

        if (accessToken==null){
            Toast.makeText(this, "Please login to continue ",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(activity_createnewpost.this, LoginActivity.class);
            startActivity(intent);
            return;
        }else {
            String authorizationHeader = "Bearer "+ accessToken;
            ApiService.apiService.getPosts(authorizationHeader, new PostRequest(createPost)).enqueue(new Callback<GetPostResponse>() {
                @Override
                public void onResponse(Call<GetPostResponse> call, Response<GetPostResponse> response) {
                        if (response.isSuccessful()){
                            GetPostResponse postResponse = response.body();
                            if(postResponse!=null){
                                ArrayList<ExtendedPost> posts = postResponse.getUser();
                                Intent intent = new Intent(activity_createnewpost.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                        else {
                            Toast.makeText(activity_createnewpost.this,"Create post failed",Toast.LENGTH_LONG).show();
                        }
                }

                @Override
                public void onFailure(Call<GetPostResponse> call, Throwable t) {
                    Toast.makeText(activity_createnewpost.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });



        }
    }


}