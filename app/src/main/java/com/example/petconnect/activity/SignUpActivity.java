package com.example.petconnect.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
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
import com.example.petconnect.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

    }
}
