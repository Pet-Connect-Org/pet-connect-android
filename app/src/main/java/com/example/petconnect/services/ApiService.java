package com.example.petconnect.services;

import com.example.petconnect.services.auth.LoginRequest;
import com.example.petconnect.services.auth.LoginResponse;
import com.example.petconnect.services.auth.SignupRequest;
import com.example.petconnect.services.auth.SignupRespone;
import com.example.petconnect.services.post.CreatePostResponse;
import com.example.petconnect.services.auth.OtpRequest;
import com.example.petconnect.services.auth.OtpResponse;
import com.example.petconnect.services.post.GetPostResponse;
import com.example.petconnect.services.post.CreatePostRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    ApiService apiService = new Retrofit.Builder().baseUrl("https://db.pet-connect.website/api/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiService.class);

    // AUTH
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/sign-up")
    Call<SignupRespone> signup(@Body SignupRequest signupRequest);

    @POST("auth/verify_user_email")
    Call<OtpResponse> verifyemail(@Body OtpRequest otpRequest);

    // POST
    @GET("posts")
    Call<GetPostResponse> getPosts(@Header("Authorization") String authorization, @HeaderMap Map<String, Number> options);

    @POST("post")
    Call<CreatePostResponse> createPost(@Header("Authorization") String authorization, @Body CreatePostRequest createPostRequest);

}