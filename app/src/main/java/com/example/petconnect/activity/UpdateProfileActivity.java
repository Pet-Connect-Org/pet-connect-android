package com.example.petconnect.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.petconnect.CustomDropdown;
import com.example.petconnect.CustomTextfield;
import com.example.petconnect.Item;
import com.example.petconnect.R;
import com.example.petconnect.activity.DrawerBaseActivity;
import com.example.petconnect.databinding.ActivityProfileBinding;
import com.example.petconnect.databinding.ActivityUpdateProfileBinding;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedAccount;
import com.example.petconnect.models.User;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.user.UpdateUserRequest;
import com.example.petconnect.services.user.UpdateUserResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends DrawerBaseActivity {
    ActivityUpdateProfileBinding activityUpdateProfileBinding;
    CustomTextfield name, dob, email, address;
    CustomDropdown gender;
    UserManager userManager;
    Intent intent;
    Button updateProfile, updatePasswword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUpdateProfileBinding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(activityUpdateProfileBinding.getRoot());

        initView();

        setInitialData();

        userManager = new UserManager(this);

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isDifferentData = checkDifferentData();

                if (isDifferentData) {
                    String new_name = name.getText().trim();
                    String new_gender = gender.getSelectedItemKey().trim();
                    String new_dob = dob.getText().trim().replaceAll("/", "-");
                    String new_address = address.getText().trim();

                    ApiService.apiService.updateUser("Bearer " + userManager.getAccessToken(), new UpdateUserRequest(new_name, new_gender, new_dob, new_address)).enqueue(new Callback<UpdateUserResponse>() {
                        @Override
                        public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                            if (response.isSuccessful()) {
                                ExtendedAccount account = userManager.getUser();
                                account.setAddress(new_address);
                                account.setName(new_name);
                                account.setSex(new_gender);

                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                Date dob = null;
                                try {
                                    dob = sdf.parse(new_dob);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                account.setBirthday(dob);

                                String token = response.body().getToken();
                                String message = response.body().getMessage();
                                userManager.saveAccessToken(token);
                                userManager.saveUser(account);

                                Toast.makeText(UpdateProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                                refreshActivity();

                            } else {
                                Toast.makeText(UpdateProfileActivity.this, "NOT success " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
                            Toast.makeText(UpdateProfileActivity.this, "Please try again later.", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });


    }

    public Boolean checkDifferentData() {
        return !name.getText().equals(userManager.getUser().getName())
                || !dob.getText().equals((new SimpleDateFormat("dd-MM-yyyy")).format(userManager.getUser().getBirthday()))
                || !address.getText().equals(userManager.getUser().getAddress())
                || !gender.getSelectedItemKey().equals(userManager.getUser().getSex());
    }

    public void refreshActivity() {
        recreate();
        setInitialData();
    }

    public void setInitialData() {
        name.setText(userManager.getUser().getName());
        dob.setText((new SimpleDateFormat("dd-MM-yyyy")).format(userManager.getUser().getBirthday()));
        email.setText(userManager.getUser().getEmail());
        address.setText(userManager.getUser().getAddress());
        gender.setDefaultItem(userManager.getUser().getSex());
    }

    public void initView() {
        name = findViewById(R.id.name_input_box);
        gender = findViewById(R.id.gender_input_box);
        dob = findViewById(R.id.dob_input_box);
        email = findViewById(R.id.email_input_box);
        address = findViewById(R.id.address_input_box);
        updateProfile = findViewById(R.id.button_update_profile);
        updatePasswword = findViewById(R.id.button_update_password);

        ArrayList<Item> genderItems = new ArrayList<>();

        genderItems.add(new Item("female", "Female"));
        genderItems.add(new Item("male", "Male"));

        gender.setItems(genderItems);
    }
}