package com.example.petconnect.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.petconnect.CustomTextfield;
import com.example.petconnect.R;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedAccount;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.auth.LoginRequest;
import com.example.petconnect.services.auth.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button submit_login_button;
    CustomTextfield email;
    CustomTextfield password;
    UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userManager = new UserManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        submit_login_button = findViewById(R.id.submit_login_button);
        email = findViewById(R.id.email_input_box);
        password = findViewById(R.id.password_input_box);

        // This is demo account for login function
        email.setText("buithuyngoc1@gmail.com");
        password.setText("buithuyngoc2003");

        submit_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit_login_button.setClickable(false);
                String email_to_submit = email.getText();
                String password_to_submit = password.getText();
                ApiService.apiService.login(new LoginRequest(email_to_submit, password_to_submit)).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();
                            ExtendedAccount account = loginResponse.getUser();
                            String token = loginResponse.getToken();
                            String message = loginResponse.getMessage();
                            userManager.saveAccessToken(token);
                            userManager.saveUser(account);

                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                            Intent intent;

                            intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                submit_login_button.setClickable(true);
            }
        });
    }
}