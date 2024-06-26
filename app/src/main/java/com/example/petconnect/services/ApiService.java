package com.example.petconnect.services;

import com.example.petconnect.services.auth.ChangePasswordRequest;
import com.example.petconnect.services.auth.ChangePasswordResponse;

import com.example.petconnect.services.comment.AddCommentRequest;
import com.example.petconnect.services.comment.AddCommentResponse;

import com.example.petconnect.services.auth.LoginRequest;
import com.example.petconnect.services.auth.LoginResponse;

import com.example.petconnect.services.auth.OtpRequest;
import com.example.petconnect.services.auth.OtpResponse;

import com.example.petconnect.services.auth.ResendRequest;
import com.example.petconnect.services.auth.ResendResponse;

import com.example.petconnect.services.auth.SignupRequest;
import com.example.petconnect.services.auth.SignupRespone;

import com.example.petconnect.services.comment.DeleteCommentResponse;

import com.example.petconnect.services.comment.LikeCommentResponse;
import com.example.petconnect.services.comment.UnlikeCommentResponse;

import com.example.petconnect.services.comment.UpdateCommentRequest;
import com.example.petconnect.services.comment.UpdateCommentResponse;

import com.example.petconnect.services.pet.CreateNewPetProfileRequest;
import com.example.petconnect.services.pet.CreateNewPetProfileResponse;

import com.example.petconnect.services.petType.GetPetTypeListResponse;

import com.example.petconnect.services.post.CreatePostResponse;
import com.example.petconnect.services.post.CreatePostRequest;

import com.example.petconnect.services.post.UpdatePostRequest;
import com.example.petconnect.services.post.UpdatePostResponse;

import com.example.petconnect.services.post.DeletePostResponse;

import com.example.petconnect.services.post.GetPostResponse;

import com.example.petconnect.services.post.LikePostResponse;
import com.example.petconnect.services.post.UnlikePostResponse;

import com.example.petconnect.services.user.FollowResponse;
import com.example.petconnect.services.user.GetUserByIdResponse;
import com.example.petconnect.services.user.UnFollowResponse;
import com.example.petconnect.services.user.UpdateUserRequest;
import com.example.petconnect.services.user.UpdateUserResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @POST("auth/resend_verification_code")
    Call<ResendResponse> ReSendOTP(@Body ResendRequest resendOtp);

    @POST("user")
    Call<UpdateUserResponse> updateUser(
            @Header("Authorization") String authorization,
            @Body UpdateUserRequest updateUserRequest
    );

    @POST("account/change_password/{id}")
    Call<ChangePasswordResponse> changePassword(@Header("Authorization") String authorization,
                                                @Path("id") Integer id,
                                                @Body ChangePasswordRequest changePasswordRequest);

    //comment

    @POST("comment")
    Call<AddCommentResponse> createComment(@Header("Authorization") String authorization,
                                           @Body AddCommentRequest commentRequest);

    @PUT("comment/{id}")
    Call<UpdateCommentResponse> updateComment(@Header("Authorization") String authorization,
                                              @Body UpdateCommentRequest commentRequest,
                                              @Path("id") int id);

    @DELETE("comment/{id}")
    Call<DeleteCommentResponse> deleteComment(@Header("Authorization") String authorization,
                                              @Path("id") int id);

    @POST("comment/like/{id}")
    Call<LikeCommentResponse> likeComment(@Header("Authorization") String authorization, @Path("id") int id);

    @POST("comment/unlike/{id}")
    Call<UnlikeCommentResponse> unlikeComment(@Header("Authorization") String authorization, @Path("id") int id);

    // POST
    @GET("posts")
    Call<GetPostResponse> getPosts(@Header("Authorization") String authorization, @Query("user_id") Integer user_id, @Query("limit") Integer limit, @Query("offset") Integer offset);

    @GET("user/{id}")
    Call<GetUserByIdResponse> getUserById(@Header("Authorization") String authorization, @Path("id") Integer id);

    @POST("post")
    Call<CreatePostResponse> createPost(@Header("Authorization") String authorization, @Body CreatePostRequest createPostRequest);

    @POST("post/like/{id}")
    Call<LikePostResponse> likepost(@Header("Authorization") String authorization,
                                    @Path("id") int id);

    @POST("post/unlike/{id}")
    Call<UnlikePostResponse> unlikepost(@Header("Authorization") String authorization, @Path("id") int id);
    
    @PUT("post/{id}")
    Call<UpdatePostResponse> updatepost(@Header("Authorization") String authorization,
                                        @Body UpdatePostRequest updatePostRequest,
                                    @Path("id") int id);

    @DELETE("post/{id}")
    Call<DeletePostResponse> deletePost(@Header("Authorization") String authorization, @Path("id") int id);

    @POST("follow/user/{id}")
    Call<FollowResponse> followUser(@Header("Authorization") String authorization,
                                    @Path("id") int id);

    @POST("unfollow/user/{id}")
    Call<UnFollowResponse> unfollowUser(@Header("Authorization") String authorization,
                                        @Path("id") int id);

    @GET("pet_type")
    Call<GetPetTypeListResponse> getPetTypeList(@Header("Authorization") String authorization);

    @POST("pet_profile")
    Call<CreateNewPetProfileResponse> createPetProfile(@Header("Authorization") String authorization, @Body CreateNewPetProfileRequest createNewPetProfileRequest);
}