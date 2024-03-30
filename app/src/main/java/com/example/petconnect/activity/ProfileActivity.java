package com.example.petconnect.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petconnect.CustomAvatar;
import com.example.petconnect.R;
import com.example.petconnect.adapter.PostListAdapter;
import com.example.petconnect.databinding.ActivityProfileBinding;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedPost;
import com.example.petconnect.models.ExtendedUser;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.post.GetPostResponse;
import com.example.petconnect.services.user.GetUserByIdResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends DrawerBaseActivity {
    ActivityProfileBinding activityProfileBinding;
    RecyclerView recyclerViewPostList;
    PostListAdapter postListAdapter;
    UserManager userManager;
    Intent intent;

    CustomAvatar profile_user_avatar;
    TextView profile_user_name;

    ExtendedUser user;

    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityProfileBinding.getRoot());

        userManager = new UserManager(this);

        Intent myintent = getIntent();
        if (myintent.hasExtra("user_id")) {
            this.user_id = Integer.parseInt(myintent.getStringExtra("user_id"));
        } else {
            this.user_id = userManager.getUser().getId();
        }

        recyclerViewPostList = findViewById(R.id.recyclerViewPostList);
        profile_user_name = findViewById(R.id.profile_user_name);
        profile_user_avatar = findViewById(R.id.profile_user_avatar);


        recyclerViewPostList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        fetchPosts();
    }

    private void fetchPosts() {
        String token = userManager.getAccessToken();
        ApiService.apiService.getUserById("Bearer " + token, this.user_id).enqueue(new Callback<GetUserByIdResponse>() {
            @Override
            public void onResponse(Call<GetUserByIdResponse> call, Response<GetUserByIdResponse> response) {
                if (response.isSuccessful()) {
                    ExtendedUser user = response.body().getData();
                    updateRecyclerView(user.getPosts());

                    profile_user_avatar.setName(user.getName());
                    profile_user_name.setText(user.getName());
                    Toast.makeText(ProfileActivity.this, "OK", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (response.code() == 401) {
                    Toast.makeText(ProfileActivity.this, "You need to login again. Someone has login to your account.", Toast.LENGTH_SHORT).show();
                    intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ProfileActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetUserByIdResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Bearer " + token, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecyclerView(List<ExtendedPost> postList) {
        postListAdapter = new PostListAdapter(ProfileActivity.this, postList);
        recyclerViewPostList.setAdapter(postListAdapter);
    }
}