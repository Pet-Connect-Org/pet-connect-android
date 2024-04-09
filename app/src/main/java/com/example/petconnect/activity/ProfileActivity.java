package com.example.petconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.petconnect.CustomAvatar;
import com.example.petconnect.R;
import com.example.petconnect.adapter.PostListAdapter;
import com.example.petconnect.adapter.ViewPagerAdapter;
import com.example.petconnect.databinding.ActivityProfileBinding;
import com.example.petconnect.fragment.FollowerFragment;
import com.example.petconnect.fragment.FollowingFragment;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedPost;
import com.example.petconnect.models.ExtendedUser;
import com.example.petconnect.models.Follow;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.user.GetUserByIdResponse;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding activityProfileBinding;
    RecyclerView recyclerViewPostList;
    PostListAdapter postListAdapter;
    UserManager userManager;
    Intent intent;
    Button profile_action_button;
    CustomAvatar profile_user_avatar;
    TextView profile_user_name;

    ExtendedUser user;
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    int user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
//        setContentView(activityProfileBinding.getRoot());

        userManager = new UserManager(this);

        Intent myintent = getIntent();
        if (myintent.hasExtra("user_id")) {
            this.user_id = Integer.parseInt(myintent.getStringExtra("user_id"));
        } else {
            this.user_id = userManager.getUser().getId();
        }
        recyclerViewPostList = findViewById(R.id.recyclerViewPostList);
        recyclerViewPostList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        profile_user_name = findViewById(R.id.profile_user_name);
        profile_user_avatar = findViewById(R.id.profile_user_avatar);
        profile_action_button = findViewById(R.id.profile_action_button);

        viewPager2 = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        adapter.addFragment(new FollowerFragment(), "Follower");
        adapter.addFragment(new FollowingFragment(), "Following");

        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(adapter.getPageTitle(position))
        ).attach();

        if (this.user_id == userManager.getUser().getId()) {
            profile_action_button.setText("Edit your profile");
        }

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
                    boolean isFollow = false;
                    for (Follow follow : user.getFollowing()) {
                        if (follow.getUser_id() == userManager.getUser().getId()) {
                            isFollow = true;
                            break;
                        }
                    }

                    if (user.getId() != userManager.getUser().getId()) {
                        profile_action_button.setText(isFollow ? "Following" : "Follow " + user.getName());
                    }
                    // Truyền danh sách follower sang FollowerFragment
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("followerList", user.getFollowers());
                    FollowerFragment followerFragment = new FollowerFragment();
                    followerFragment.setArguments(bundle);

                    // Truyền danh sách following sang FollowingFragment
                    Bundle followingBundle = new Bundle();
                    followingBundle.putParcelableArrayList("followingList", user.getFollowing());
                    FollowingFragment followingFragment = new FollowingFragment();
                    followingFragment.setArguments(followingBundle);

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
        if (postList != null) {
            postListAdapter = new PostListAdapter(ProfileActivity.this, postList);
            recyclerViewPostList.setAdapter(postListAdapter);
        }
    }
}
