package com.example.petconnect.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.petconnect.CustomDialog;
import com.example.petconnect.CustomTextfield;
import com.example.petconnect.R;
import com.example.petconnect.manager.AccessTokenManager;
import com.example.petconnect.models.ExtendedAccount;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.auth.LoginRequest;
import com.example.petconnect.services.auth.LoginResponse;
import com.example.petconnect.stores.UserStore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button submit_login_button;
    CustomTextfield email;
    CustomTextfield password;
    CustomDialog dialog;
    AccessTokenManager accessTokenManager;

    Button toSignup;

    UserStore userStore = UserStore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        submit_login_button = findViewById(R.id.submit_login_button);
        toSignup = findViewById(R.id.toSignup);
        email = findViewById(R.id.email_input_box);
        password= findViewById(R.id.password_input_box);

        // This is demo account for log in function
        email.setText("buithuyngoc1@gmail.com");
        password.setText("buithuyngoc2003");
        accessTokenManager = new AccessTokenManager(getApplicationContext());

        toSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        submit_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_to_submit = email.getText();
                String password_to_submit = password.getText();
//                Toast.makeText(LoginActivity.this, email_to_submit+password_to_submit, Toast.LENGTH_LONG).show();
//                CustomDialog.showDialog(LoginActivity.this, email_to_submit, password_to_submit);
                ApiService.apiService.login(new LoginRequest(email_to_submit, password_to_submit)).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();
                            ExtendedAccount account = loginResponse.getUser();
                            String token = loginResponse.getToken();
                            String message = loginResponse.getMessage();

                            userStore.setAccessToken(token);
                            userStore.setAccountInformation(account);

                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                            Intent intent;
                            intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Handle unsuccessful login
                            Toast.makeText(LoginActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        // Handle network errors or unexpected exceptions
                        Toast.makeText(LoginActivity.this, "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}