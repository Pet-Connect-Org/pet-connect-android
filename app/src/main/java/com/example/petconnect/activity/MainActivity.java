package com.example.petconnect.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.petconnect.R;
import com.example.petconnect.adapter.PostListAdapter;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedPost;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.post.GetPostResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewPostList;
    PostListAdapter postListAdapter;
    UserManager userManager;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userManager = new UserManager(this);

        recyclerViewPostList = findViewById(R.id.recyclerViewPostList);
        recyclerViewPostList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        fetchPosts();
    }

    private void fetchPosts() {
        String token = userManager.getAccessToken();

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManager.clearAccessToken();
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Map<String, Number> queryOptions = new HashMap<>();
        queryOptions.put("limit", 20);
        queryOptions.put("offset", 0);

        ApiService.apiService.getPosts("Bearer " + token, queryOptions).enqueue(new Callback<GetPostResponse>() {
            @Override
            public void onResponse(Call<GetPostResponse> call, Response<GetPostResponse> response) {
                Toast.makeText(MainActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();

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
//        postListAdapter.setCurrentUser(userManager.getCurrentUser()); // Thêm dòng này để thiết lập currentUser
        recyclerViewPostList.setAdapter(postListAdapter);
    }
}
