package com.example.petconnect.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.petconnect.R;
import com.example.petconnect.adapter.PostListAdapter;
import com.example.petconnect.databinding.ActivityMainBinding;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedPost;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.post.GetPostResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends DrawerBaseActivity {

    ActivityMainBinding activityMainBinding;
    RecyclerView recyclerViewPostList;
    PostListAdapter postListAdapter;
    UserManager userManager;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        userManager = new UserManager(this);

        recyclerViewPostList = findViewById(R.id.recyclerViewPostList);
        recyclerViewPostList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        fetchPosts();
    }

    private void fetchPosts() {
        String token = userManager.getAccessToken();

        ApiService.apiService.getPosts("Bearer " + token, null, 20, 0).enqueue(new Callback<GetPostResponse>() {
            @Override
            public void onResponse(Call<GetPostResponse> call, Response<GetPostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ExtendedPost> postList = response.body().getPostList();
                    updateRecyclerView(postList);
                    return;
                }
                if (response.code() == 401) {
                    Toast.makeText(MainActivity.this, "You need to login again. Someone has login to your account.", Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetPostResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Bearer " + token, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecyclerView(List<ExtendedPost> postList) {
        postListAdapter = new PostListAdapter(MainActivity.this, postList);
        recyclerViewPostList.setAdapter(postListAdapter);
    }
}
