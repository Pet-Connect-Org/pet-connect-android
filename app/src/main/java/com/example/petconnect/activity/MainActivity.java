package com.example.petconnect.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.petconnect.CustomDropdown;
import com.example.petconnect.Item;
import com.example.petconnect.R;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedAccount;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    TextView accessToken;
    TextView userInformation;
    Button clear;
    UserManager userManager;
    Intent intent;

    CustomDropdown dropdownDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dropdownDemo = findViewById(R.id.dropdown_demo);

        Item[] demo = new Item[] {
                new Item("Key1", "Value1"),
                new Item("Key2", "Value2")
        };

        dropdownDemo.setItems(demo);

        userManager = new UserManager(this);
        clear = findViewById(R.id.clear);
        ExtendedAccount user = userManager.getUser();

        accessToken = findViewById(R.id.accessToken);
        userInformation = findViewById(R.id.userInformation);

        System.out.println(userManager.getUser());
        accessToken.setText(accessToken.getText() + " " + userManager.getAccessToken());
        userInformation.setText(userInformation.getText() + " " + user.getEmail());

        dropdownDemo.showContextMenu();

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManager.clearAccessToken();
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}