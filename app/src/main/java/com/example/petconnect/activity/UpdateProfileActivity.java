package com.example.petconnect.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.example.petconnect.services.auth.ChangePasswordRequest;
import com.example.petconnect.services.auth.ChangePasswordResponse;
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
    CustomTextfield name, dob, email, address, pw_input_box, pw_re_new_input_box, pw_new_input_box;
    CustomDropdown gender;
    UserManager userManager;
    TextView tick1, tick2, tick3;
    TextView labelsub1, labelsub2, labelsub3;
    Intent intent;
    Button updateProfile, updatePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUpdateProfileBinding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(activityUpdateProfileBinding.getRoot());
        userManager = new UserManager(this);

        initView();

        setInitialData();

        Toast.makeText(UpdateProfileActivity.this, String.valueOf(userManager.getUser().getAccount_id()), Toast.LENGTH_SHORT).show();

        updatePassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Boolean canUpdate = !pw_input_box.getText().isEmpty() && !pw_new_input_box.getText().isEmpty() && !pw_re_new_input_box.getText().isEmpty();

                if (canUpdate && checkPassword(pw_new_input_box.getText(), pw_re_new_input_box.getText())) {
                    ApiService.apiService.changePassword("Bearer " + userManager.getAccessToken(), userManager.getUser().getAccount_id(), new ChangePasswordRequest(pw_input_box.getText(), pw_new_input_box.getText(), pw_re_new_input_box.getText())).enqueue(new Callback<ChangePasswordResponse>() {
                        @Override
                        public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(UpdateProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                pw_input_box.setText("");
                                pw_new_input_box.setText("");
                                pw_re_new_input_box.setText("");
                            } else {
                                Toast.makeText(UpdateProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                            Toast.makeText(UpdateProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

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
        updatePassword = findViewById(R.id.button_update_password);
        pw_input_box = findViewById(R.id.pw_input_box);
        pw_re_new_input_box = findViewById(R.id.pw_re_new_input_box);
        pw_new_input_box = findViewById(R.id.pw_new_input_box);

        tick1 = findViewById(R.id.tick1);
        tick2 = findViewById(R.id.tick2);
        tick3 = findViewById(R.id.tick3);


        labelsub1 = findViewById(R.id.labelsub1);
        labelsub2 = findViewById(R.id.labelsub2);
        labelsub3 = findViewById(R.id.labelsub3);

        ArrayList<Item> genderItems = new ArrayList<>();

        genderItems.add(new Item("female", "Female"));
        genderItems.add(new Item("male", "Male"));

        gender.setItems(genderItems);
    }

    public Boolean checkPassword(String pw_to_submit, String re_pw_to_submit) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d).+";
        Boolean pass = true;

        if (pw_to_submit.length() >= 6 && pw_to_submit.matches(passwordPattern)) {
            labelsub1.setTextColor(ContextCompat.getColor(UpdateProfileActivity.this, R.color.correct));
            tick1.setBackgroundTintList(ContextCompat.getColorStateList(UpdateProfileActivity.this, R.color.correct));
            labelsub2.setTextColor(ContextCompat.getColor(UpdateProfileActivity.this, R.color.correct));
            tick2.setBackgroundTintList(ContextCompat.getColorStateList(UpdateProfileActivity.this, R.color.correct));
        } else {
            pass = false;
            labelsub1.setTextColor(ContextCompat.getColor(UpdateProfileActivity.this, R.color.third));
            tick1.setBackgroundTintList(ContextCompat.getColorStateList(UpdateProfileActivity.this, R.color.third));
            labelsub2.setTextColor(ContextCompat.getColor(UpdateProfileActivity.this, R.color.third));
            tick2.setBackgroundTintList(ContextCompat.getColorStateList(UpdateProfileActivity.this, R.color.third));

        }

        if (pw_to_submit.equals(re_pw_to_submit)) {
            labelsub3.setTextColor(ContextCompat.getColor(UpdateProfileActivity.this, R.color.correct));
            tick3.setBackgroundTintList(ContextCompat.getColorStateList(UpdateProfileActivity.this, R.color.correct));

        } else {
            pass = false;

            labelsub3.setTextColor(ContextCompat.getColor(UpdateProfileActivity.this, R.color.third));
            tick3.setBackgroundTintList(ContextCompat.getColorStateList(UpdateProfileActivity.this, R.color.third));
        }
        return pass;
    }
}