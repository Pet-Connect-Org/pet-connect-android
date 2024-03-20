package com.example.petconnect.activity;

import static android.graphics.PorterDuff.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.ImageViewCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petconnect.CustomTextfield;
import com.example.petconnect.CustomTextfield.OnTextChangeListener;
import com.example.petconnect.R;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    CustomTextfield name, gender, dob, email, address, pw, re_pw;
    Button btn_signup, btn_fb, btn_gg, btn_signin;

    TextView tick1,tick2, tick3;

    DatePickerDialog picker;
    TextView labelsub1, labelsub2, labelsub3;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d).+";

    @SuppressLint("WrongViewCast")
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
        btn_fb = findViewById(R.id.btn_fb);
        btn_gg = findViewById(R.id.btn_gg);
        btn_signin = findViewById(R.id.btn_signin);

        tick1 = findViewById(R.id.tick1);
        tick2 = findViewById(R.id.tick2);
        tick3 = findViewById(R.id.tick3);


        labelsub1 = findViewById(R.id.labelsub1);
        labelsub2 = findViewById(R.id.labelsub2);
        labelsub3 = findViewById(R.id.labelsub3);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);


                //date picker
                picker = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dob.setText(dayOfMonth+ "/" + (month + 1) + "/" + year );
                    }
                } , year, month, day);
                picker.show();
            }
        });


            btn_signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name_to_submit = name.getText().toString();
                    String email_to_submit = email.getText().toString().trim();
                    String pw_to_submit = pw.getText().toString();
                    String re_pw_to_submit = re_pw.getText().toString();

                    if ( name_to_submit.isEmpty() || email_to_submit.isEmpty() || pw_to_submit.isEmpty() || re_pw_to_submit.isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!email_to_submit.matches(emailPattern)) {
                        Toast.makeText(SignUpActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    checkPassword(pw_to_submit, re_pw_to_submit);
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
