package com.example.petconnect.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.petconnect.CustomDropdown;
import com.example.petconnect.CustomTextfield;
import com.example.petconnect.Item;
import com.example.petconnect.R;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.auth.SignupRequest;
import com.example.petconnect.services.auth.SignupRespone;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    CustomTextfield name, dob, email, address, pw, re_pw;
    CustomDropdown gender;
    Button btn_signup, btn_signin;
    TextView tick1, tick2, tick3;
    DatePickerDialog picker;
    TextView labelsub1, labelsub2, labelsub3;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d).+";
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name_input_box);
        gender = findViewById(R.id.gender_input_box);
        dob = findViewById(R.id.dob_input_box);
        email = findViewById(R.id.email_input_box);
        address = findViewById(R.id.address_input_box);
        pw = findViewById(R.id.pw_input_box);
        re_pw = findViewById(R.id.confirm_pw);

        btn_signup = findViewById(R.id.btn_signup);
        btn_signin = findViewById(R.id.btn_signin);

        tick1 = findViewById(R.id.tick1);
        tick2 = findViewById(R.id.tick2);
        tick3 = findViewById(R.id.tick3);


        labelsub1 = findViewById(R.id.labelsub1);
        labelsub2 = findViewById(R.id.labelsub2);
        labelsub3 = findViewById(R.id.labelsub3);


        // declare and initialize array
        Item[] genderItems = new Item[]{
                new Item("female", "Female"),
                new Item("male", "Male")
        };
        gender.setItems(genderItems);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoading) {
                    return;
                }
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String formattedDate = String.format(Locale.getDefault(), "%02d-%02d-%d", dayOfMonth, month + 1, year);
                        dob.setText(formattedDate);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoading) {
                    return;
                }

                String email_to_submit = email.getText().trim();
                String pw_to_submit = pw.getText();
                String re_pw_to_submit = re_pw.getText();
                String name_to_submit = name.getText();
                String gender_to_submit = gender.getSelectedItemKey();
                String dob_to_submit = dob.getText();
                String address_to_submit = address.getText();

                if (name_to_submit.isEmpty() || email_to_submit.isEmpty() || pw_to_submit.isEmpty() || re_pw_to_submit.isEmpty() || gender_to_submit.isEmpty() || dob_to_submit.isEmpty() || address_to_submit.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email_to_submit.matches(emailPattern)) {
                    Toast.makeText(SignUpActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }
                checkPassword(pw_to_submit, re_pw_to_submit);

                // Set isLoading to true before making the signup request
                isLoading = true;

                ApiService.apiService.signup(new SignupRequest(email_to_submit, pw_to_submit, name_to_submit, re_pw_to_submit, gender_to_submit, dob_to_submit, address_to_submit)).enqueue(new Callback<SignupRespone>() {
                    @Override
                    public void onResponse(Call<SignupRespone> call, Response<SignupRespone> response) {
                        // Reset isLoading to false after the signup request is completed
                        isLoading = false;
                        if (response.isSuccessful()) {
                            SignupRespone signupRespone = response.body();
                            String token = signupRespone.getAccessToken();
                            String message = signupRespone.getMessage();

                            Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle unsuccessful signup
                            Toast.makeText(SignUpActivity.this, "Signup failed.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SignupRespone> call, Throwable t) {
                        // Reset isLoading to false after the signup request is completed
                        isLoading = false;
                        // Handle network errors or unexpected exceptions
                        Toast.makeText(SignUpActivity.this, "Signup failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    public void checkPassword(String pw_to_submit, String re_pw_to_submit) {
        if (pw_to_submit.length() >= 6 && pw_to_submit.matches(passwordPattern)) {
            labelsub1.setTextColor(ContextCompat.getColor(SignUpActivity.this, R.color.correct));
            tick1.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this, R.color.correct));
            labelsub2.setTextColor(ContextCompat.getColor(SignUpActivity.this, R.color.correct));
            tick2.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this, R.color.correct));
        } else {
            labelsub1.setTextColor(ContextCompat.getColor(SignUpActivity.this, R.color.third));
            tick1.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this, R.color.third));
            labelsub2.setTextColor(ContextCompat.getColor(SignUpActivity.this, R.color.third));
            tick2.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this, R.color.third));

        }

        if (pw_to_submit.equals(re_pw_to_submit)) {
            labelsub3.setTextColor(ContextCompat.getColor(SignUpActivity.this, R.color.correct));
            tick3.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this, R.color.correct));

        } else {
            labelsub3.setTextColor(ContextCompat.getColor(SignUpActivity.this, R.color.third));
            tick3.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this, R.color.third));
        }
    }

}
