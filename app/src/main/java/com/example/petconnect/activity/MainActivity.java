package com.example.petconnect.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.petconnect.R;
import com.example.petconnect.stores.UserStore;

public class MainActivity extends AppCompatActivity {

    TextView accessToken;
    TextView userInformation;

    UserStore userStore = UserStore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        accessToken = findViewById(R.id.accessToken);
        userInformation = findViewById(R.id.userInformation);

        accessToken.setText(accessToken.getText()+ " " + userStore.getAccessToken());

        userInformation.setText(userInformation.getText() + " " + userStore.getAccountInformation().getEmail());

    }
}