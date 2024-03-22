package com.example.petconnect.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petconnect.R;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.auth.OtpRequest;
import com.example.petconnect.services.auth.OtpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {
    EditText otp1, otp2, otp3, otp4, otp5, otp6;
    TextView resendBtn;
    // true after every 60s
    boolean resendEnable = false;
    //resend time in second
    int resendTime = 60;
    ImageButton btnOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);
        resendBtn = findViewById(R.id.resendBtn);
        btnOtp = findViewById(R.id.btnOtp);

        btnOtp.setOnClickListener(new View.OnClickListener (){
            @Override
            public void onClick(View view) {
                String number1 = otp1.getText().toString().trim();
                String number2 = otp2.getText().toString().trim();
                String number3 = otp3.getText().toString().trim();
                String number4 = otp4.getText().toString().trim();
                String number5 = otp5.getText().toString().trim();
                String number6 = otp6.getText().toString().trim();

//                void startCountDownTimer() {
//                    resendEnable = fasle;
//                    resendBtn.setTextColor(Color.parseColorColor("#99000000"));
//                    new CountDownTimer(resendTime * 60, countDownInterval: 100){
//                        @Override
//                           public void onTick(long millisUntilFinished)
//                        {
//                            resendBtn.setText("Resend Code( "+(millisUntilFinished / 60)+")");
//                        }
//                        @Override
//                        public void onFinished() {
//                            resendEnable = true;
//                            resendBtn.setTextColor(getResources().getColor(R.color.primary));
//
//                        }
//                    }



//                }.start();


                ApiService.apiService.verifyemail(new OtpRequest(number1, number2, number3, number4, number5, number6)).enqueue(new Callback<OtpResponse>() {
                    @Override
                    public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                        if (response.isSuccessful()) {
                            OtpResponse OtpResponse = response.body();

                            String status = OtpResponse.getStatus();
                            String message = OtpResponse.getMessage();

                            Toast.makeText(OtpActivity.this, message, Toast.LENGTH_SHORT).show();

                            Intent intent;

                            intent = new Intent(OtpActivity.this, LoginActivity.class);
                            startActivity(intent);

                        } else {
                            // Handle unsuccessful otp
                            Toast.makeText(OtpActivity.this, "Otp failed.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OtpResponse> call, Throwable t) {
                        // Handle network errors or unexpected exceptions
                        Toast.makeText(OtpActivity.this, "Otp failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }


        });

    }
}