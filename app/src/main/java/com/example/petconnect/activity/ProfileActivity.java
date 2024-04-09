package com.example.petconnect.activity;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petconnect.CustomAvatar;
import com.example.petconnect.R;
import com.example.petconnect.adapter.PostListAdapter;
import com.example.petconnect.databinding.ActivityProfileBinding;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedPost;
import com.example.petconnect.models.ExtendedUser;
import com.example.petconnect.models.Follow;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.user.FollowResponse;
import com.example.petconnect.services.user.GetUserByIdResponse;
import com.example.petconnect.services.user.UnFollowResponse;

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
    Button profile_action_button;
    CustomAvatar profile_user_avatar;
    TextView profile_user_name;
    ExtendedUser user;

    int user_id;

    boolean isFollow = false;  // Khởi tạo biến isFollow

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
        profile_action_button = findViewById(R.id.profile_action_button);


        recyclerViewPostList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        fetchUserDetails();

        fetchPosts();
    }
    private void fetchUserDetails() {
        String token = userManager.getAccessToken();
        ApiService.apiService.getUserById("Bearer " + token, this.user_id).enqueue(new Callback<GetUserByIdResponse>() {
            @Override
            public void onResponse(Call<GetUserByIdResponse> call, Response<GetUserByIdResponse> response) {
                if (response.isSuccessful()) {
                    user = response.body().getData();
                    displayUserData();
                } else if (response.code() == 401) {
                    handleUnauthorized();
                } else {
                    Toast.makeText(ProfileActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserByIdResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error retrieving user data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void displayUserData() {
        profile_user_avatar.setName(user.getName());
        profile_user_name.setText(user.getName());

        if (this.user_id == userManager.getUser().getId()) {
            profile_action_button.setText("Edit your profile");
            profile_action_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ProfileActivity.this, UpdateProfileActivity.class));
                }
            });
        } else {
            updateFollowButton();
        }
    }
    private void updateFollowButton() {
        for (Follow follow : user.getFollowing()) {
            if (follow.getUser_id() == userManager.getUser().getId()) {
                profile_action_button.setText("Following");
                isFollow = true;
                break;
            }
        }
        profile_action_button.setText(isFollow ? "Following" : "Follow " + user.getName());
        profile_action_button.setOnClickListener(new View.OnClickListener() {
            String token = userManager.getAccessToken();
            @Override
            public void onClick(View view) {
                if (!isFollow) {
                    ApiService.apiService.followUser("Bearer " + token, user.getId()).enqueue(new Callback<FollowResponse>() {
                        @Override
                        public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
                            if(response.isSuccessful()){
                                profile_action_button.setText("Following");
                                isFollow = true;
                            }
                        }
                        @Override
                        public void onFailure(Call<FollowResponse> call, Throwable t) {
                            Toast.makeText(ProfileActivity.this, "Error when following user", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {

                    ApiService.apiService.unfollowUser("Bearer " + token, user.getId()).enqueue(new Callback<UnFollowResponse>() {
                        @Override
                        public void onResponse(Call<UnFollowResponse> call, Response<UnFollowResponse> response) {
                            if(response.isSuccessful()){
                                profile_action_button.setText("Follow " + user.getName());
                                isFollow = false;
                            }
                        }
                        @Override
                        public void onFailure(Call<UnFollowResponse> call, Throwable t) {
                            Toast.makeText(ProfileActivity.this, "Error when unfollowing user", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    private void fetchPosts() {
        String token = userManager.getAccessToken();
        ApiService.apiService.getUserById("Bearer " + token, this.user_id).enqueue(new Callback<GetUserByIdResponse>() {
            @Override
            public void onResponse(Call<GetUserByIdResponse> call, Response<GetUserByIdResponse> response) {
                if (response.isSuccessful()) {
                    user = response.body().getData();
                    updateRecyclerView(user.getPosts());

                    profile_user_avatar.setName(user.getName());
                    profile_user_name.setText(user.getName());
                    isFollow = false;
                    for (Follow follow : user.getFollowing()) {
                        if (follow.getUser_id() == userManager.getUser().getId()) {
                            isFollow = true;
                            break;
                        }
                    }

                    if (user.getId() != userManager.getUser().getId()) {
                        profile_action_button.setText(isFollow ? "Following" : "Follow " + user.getName());
                    }

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
    private void handleUnauthorized() {
        Toast.makeText(ProfileActivity.this, "You need to login again. Someone has logged into your account.", Toast.LENGTH_SHORT).show();
        intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateRecyclerView(List<ExtendedPost> postList) {
        if (postList != null) {
            postListAdapter = new PostListAdapter(ProfileActivity.this, postList);
            recyclerViewPostList.setAdapter(postListAdapter);
        }
    }
}