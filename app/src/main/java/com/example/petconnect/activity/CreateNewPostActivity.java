package com.example.petconnect.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petconnect.R;

import com.example.petconnect.manager.UserManager;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.post.CreatePostResponse;
import com.example.petconnect.services.post.CreatePostRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewPostActivity extends AppCompatActivity {
    Button btnPost;
    EditText txtStatusPost;
    TextView create_post_username, close_create_post, uploadImage;

    UserManager userManager;
    ImageView imageViewPost;
    String imageURL1;
    Uri uri;
    boolean isUploadImg1;
    private ActivityResultLauncher<Intent> activityResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnewpost);
        btnPost = findViewById(R.id.btnPost);
        txtStatusPost = findViewById(R.id.txtstatus_post);
        create_post_username = findViewById(R.id.create_post_username);
        close_create_post = findViewById(R.id.close_create_post);
        imageViewPost = findViewById(R.id.uploadPicture1);
        uploadImage = findViewById(R.id.uploadImage);
        userManager = new UserManager(this);
        create_post_username.setText(userManager.getUser().getName());


        close_create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNewPostActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPost();

//                if (isUploadImg1) {
//                    saveImage();
//                }
            }
        });
//        uploadImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent photoPicker = new Intent(Intent.ACTION_PICK);
//                photoPicker.setType("image/*");
//                activityResultLauncher.launch(photoPicker);
//
//            }
//        });
//        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if (result.getResultCode() == Activity.RESULT_OK) {
//                    Intent data = result.getData();
//                    uri = data.getData();
//                    imageViewPost.setImageURI(uri);
//                    isUploadImg1 = true;
//                } else {
//                    isUploadImg1 = false;
//                    Toast.makeText(CreateNewPostActivity.this, "No image selected", Toast.LENGTH_LONG).show();
//                }
//            }
//
//
//        });
    }


    private void uploadPost() {
        String createPost = txtStatusPost.getText().toString();
        String accessToken = (new UserManager(this)).getAccessToken(); //lấy đối tượng hiện tại đang đăng nhập
        if (accessToken == null) { // kiểm tra người dùng hiện tại có đăng nhập hay không nếu ko thì phải đăng nhập
            Toast.makeText(this, "Please login to continue ", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CreateNewPostActivity.this, LoginActivity.class);
            startActivity(intent);
            return;
        } else {
            String authorizationHeader = "Bearer " + accessToken;
            ApiService.apiService.createPost(authorizationHeader, new CreatePostRequest(createPost)).enqueue(new Callback<CreatePostResponse>() {
                @Override
                public void onResponse(Call<CreatePostResponse> call, Response<CreatePostResponse> response) {
                    if (response.isSuccessful()) {
                        CreatePostResponse postResponse = response.body(); //trả về nội dung phản hồi từ máy chủ
                        if (postResponse != null) {
                            // postResponse.getData().getId();
//                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//
//// Tham chiếu đến đường dẫn dữ liệu
//                            DatabaseReference databaseReference = firebaseDatabase.getReference("path/to/data");
                            Toast.makeText(CreateNewPostActivity.this, "Post created successfully.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(CreateNewPostActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                    } else {
                        Toast.makeText(CreateNewPostActivity.this, "Create post failed.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<CreatePostResponse> call, Throwable t) {
                    Toast.makeText(CreateNewPostActivity.this, "Failed to create post. Please try again", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

//    public void saveImage() {
//        if (uri != null) {
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("ProductImgage").child(Objects.requireNonNull(uri.getLastPathSegment()));
//
//            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                    while (!uriTask.isComplete()) ;
//                    Uri urlImage = uriTask.getResult();
//                    imageURL1 = String.valueOf(urlImage);
////                    onClickPushData();
//                    // addProductToFirebase();
//                    uploadPost();
//
//                }
//            });
//        }
//
//
//    }
}