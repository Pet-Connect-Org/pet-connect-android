package com.example.petconnect.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petconnect.R;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.auth.OtpRequest;
import com.example.petconnect.services.auth.OtpResponse;
import com.example.petconnect.services.auth.ResendRequest;
import com.example.petconnect.services.auth.ResendResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {
    EditText otp1, otp2, otp3, otp4, otp5, otp6;
    Button btnOtp, toLogin;
    TextView txtWrong, txtReceive, txtResend;
    CountDownTimer countDownTimer;
    String email;

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
        txtWrong = findViewById(R.id.txtWrong);
        txtReceive = findViewById(R.id.txtReceive);
        txtResend = findViewById(R.id.txtResend);
        btnOtp = findViewById(R.id.btnOtp);
        toLogin = findViewById(R.id.toLogin);

        // chuyen man hi hinh intent
        Intent myintent = getIntent();
        email = myintent.getStringExtra("email");

        // goi phuong thuc dich chuyen thoi gian sau khi chuyen man hinh otp
        startResendCountdown();

        // goi phuong thuc add
        addTextWatcher((EditText) otp1, (EditText) otp2);
        addTextWatcher((EditText) otp2, (EditText) otp3);
        addTextWatcher((EditText) otp3, (EditText) otp4);
        addTextWatcher((EditText) otp4, (EditText) otp5);
        addTextWatcher((EditText) otp5, (EditText) otp6);

        // xu li su kien sau khi click button
        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAnyTextViewNull()) {
                    return;
                }

                String number1 = otp1.getText().toString().trim();
                String number2 = otp2.getText().toString().trim();
                String number3 = otp3.getText().toString().trim();
                String number4 = otp4.getText().toString().trim();
                String number5 = otp5.getText().toString().trim();
                String number6 = otp6.getText().toString().trim();

                String number = number1 + number2 + number3 + number4 + number5 + number6;

                // Gửi OTP lên server để xác minh
                ApiService.apiService.verifyemail(new OtpRequest(email, number)).enqueue(new Callback<OtpResponse>() {
                    @Override
                    public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                        if (response.isSuccessful()) {
                            OtpResponse otpResponse = response.body();
                            String message = otpResponse.getMessage();

                            Toast.makeText(OtpActivity.this, message, Toast.LENGTH_SHORT).show();
                            // Chuyển qua màn Login
                            Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(OtpActivity.this, "Otp failed.", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<OtpResponse> call, Throwable t) {
                        Toast.makeText(OtpActivity.this, "Otp failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    //Kiểm tra xem có bất kỳ TextView nào có giá trị null không
    private boolean isAnyTextViewNull() {
        return otp1.getText().toString().isEmpty() ||
                otp2.getText().toString().isEmpty() ||
                otp3.getText().toString().isEmpty() ||
                otp4.getText().toString().isEmpty() ||
                otp5.getText().toString().isEmpty() ||
                otp6.getText().toString().isEmpty();
    }

    // Phuong thuc addTextWatcher de nhap chuyen text tu dong
    private void addTextWatcher(final EditText currentEditText, final EditText nextEditText) {
        currentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    nextEditText.requestFocus();
                }
            }
        });
    }

    // Bắt đầu đếm ngược cho việc gửi lại OTP
    private void startResendCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Hủy đếm ngược hiện tại trước khi khởi động mới
        }
        countDownTimer = new CountDownTimer(60000, 1000) { // 30 giây, mỗi lần giảm 1 giây
            @Override
            public void onTick(long millisUntilFinished) {
                txtResend.setTextColor(getResources().getColor(android.R.color.holo_red_dark)); // Đổi màu văn bản thành màu đỏ
                txtResend.setText("Resend in " + millisUntilFinished / 1000 + "s"); // Hiển thị số giây còn lại
            }

            @Override
            public void onFinish() {
                txtWrong.setTextColor(getResources().getColor(android.R.color.holo_red_dark)); // Đổi màu văn bản thành màu đỏ
                txtReceive.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                //txtResend.setTextColor(getResources().getColor(android.R.color.black)); // Đổi màu văn bản về màu đen khi kết thúc
                txtResend.setText("Resend again");// Hiển thị lại văn bản "Resend"


                txtResend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (email != null && !email.isEmpty()) {
                            ApiService.apiService.ReSendOTP(new ResendRequest(email)).enqueue(new Callback<ResendResponse>() {
                                @Override
                                public void onResponse(Call<ResendResponse> call, Response<ResendResponse> response) {
                                    if (response.isSuccessful()) {
                                        ResendResponse resendResponse = response.body();
                                        String message = resendResponse.getMessage();
                                        Toast.makeText(OtpActivity.this, message, Toast.LENGTH_SHORT).show();
                                        startResendCountdown();
                                    } else {
                                        Toast.makeText(OtpActivity.this, "Resend otp failed.", Toast.LENGTH_SHORT).show();
                                        startResendCountdown();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResendResponse> call, Throwable t) {
                                    startResendCountdown();
                                    Toast.makeText(OtpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(OtpActivity.this, "Email is not valid.", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Dừng đếm ngược nếu activity bị hủy
        }
    }
}
