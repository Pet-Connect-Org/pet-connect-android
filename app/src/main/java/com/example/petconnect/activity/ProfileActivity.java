package com.example.petconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.CustomAvatar;
import com.example.petconnect.R;
import com.example.petconnect.adapter.FollowAdapter;
import com.example.petconnect.adapter.PostListAdapter;
import com.example.petconnect.databinding.ActivityProfileBinding;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedFollow;
import com.example.petconnect.models.ExtendedPost;
import com.example.petconnect.models.ExtendedUser;
import com.example.petconnect.models.Follow;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.user.FollowResponse;
import com.example.petconnect.services.user.GetUserByIdResponse;
import com.google.android.material.tabs.TabLayout;
import com.example.petconnect.services.user.UnFollowResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends DrawerBaseActivity {
    ActivityProfileBinding activityProfileBinding;
    RecyclerView recyclerViewPostList, recyclerViewFollow;
    PostListAdapter postListAdapter;
    FollowAdapter followListAdapter;
    UserManager userManager;
    Intent intent;
    Button profile_action_button, button_create_new_pet;
    CustomAvatar profile_user_avatar;
    TextView profile_user_name;
    ExtendedUser user;
    TabLayout tabLayout;
    int user_id;

    ArrayList<ExtendedFollow> followerList, followingList;

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
        recyclerViewFollow = findViewById(R.id.recyclerViewFollow);

        profile_user_name = findViewById(R.id.profile_user_name);
        profile_user_avatar = findViewById(R.id.profile_user_avatar);
        profile_action_button = findViewById(R.id.profile_action_button);
        button_create_new_pet = findViewById(R.id.button_create_new_pet);

        tabLayout = findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Follower"));
        tabLayout.addTab(tabLayout.newTab().setText("Following"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText() == "Follower") {
                    updateRecyclerFollowView(followerList);
                } else if (tab.getText() == "Following") {
                    updateRecyclerFollowView(followingList);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        recyclerViewPostList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewFollow.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if (this.user_id == userManager.getUser().getId()) {
            profile_action_button.setText("Edit your profile");
            button_create_new_pet.setVisibility(View.VISIBLE);

        }

        button_create_new_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, CreateNewPetActivity.class));
            }
        });

        fetchUserDetails();
    }

    private void displayUserData() {
        profile_user_avatar.setName(user.getName());
        profile_user_name.setText(user.getName());

        followerList = user.getFollowers();
        followingList = user.getFollowing();
        updateRecyclerFollowView(user.getFollowers());

        updateRecyclerView(user.getPosts());

        if (this.user_id == userManager.getUser().getId()) {
            profile_action_button.setText("Edit your profile");
            profile_action_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ProfileActivity.this, UpdateProfileActivity.class));
                }
            });
        } else {
            for (Follow follow : user.getFollowers()) {
                if (follow.getUser_id() == userManager.getUser().getId()) {
                    isFollow = true;
                    break;
                }
            }
            updateFollowButton();
        }
    }

    private void updateFollowButton() {

        profile_action_button.setText(isFollow ? "Following" : "Follow " + user.getName());
        profile_action_button.setOnClickListener(new View.OnClickListener() {
            String token = userManager.getAccessToken();

            @Override
            public void onClick(View view) {
                if (!isFollow) {
                    ApiService.apiService.followUser("Bearer " + token, user.getId()).enqueue(new Callback<FollowResponse>() {
                        @Override
                        public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
                            if (response.isSuccessful()) {
                                profile_action_button.setText("Following");
                                isFollow = true;
                                Toast.makeText(ProfileActivity.this, "Follow success", Toast.LENGTH_SHORT).show();

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
                            if (response.isSuccessful()) {
                                profile_action_button.setText("Follow " + user.getName());
                                Toast.makeText(ProfileActivity.this, "Unfollow success", Toast.LENGTH_SHORT).show();

                                isFollow = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<UnFollowResponse> call, Throwable t) {
                            Toast.makeText(ProfileActivity.this, "Error when unfollow user", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    private void fetchUserDetails() {
        String token = userManager.getAccessToken();
        ApiService.apiService.getUserById("Bearer " + token, this.user_id).enqueue(new Callback<GetUserByIdResponse>() {
            @Override
            public void onResponse(Call<GetUserByIdResponse> call, Response<GetUserByIdResponse> response) {
                if (response.isSuccessful()) {
                    user = response.body().getData();
                    displayUserData();
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
        if (postList != null) {
            postListAdapter = new PostListAdapter(ProfileActivity.this, postList);
            recyclerViewPostList.setAdapter(postListAdapter);
        }
    }

    private void updateRecyclerFollowView(List<ExtendedFollow> followList) {
        if (followList != null) {
            followListAdapter = new FollowAdapter(ProfileActivity.this, followList);
            recyclerViewFollow.setAdapter(followListAdapter);
        }
    }
}
