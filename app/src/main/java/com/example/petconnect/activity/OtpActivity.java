package com.example.petconnect.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {
    EditText otp1, otp2, otp3, otp4, otp5, otp6;
    ImageButton btnOtp;

    TextView txtWrong, txtResend;
    CountDownTimer countDownTimer;

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
        txtResend = findViewById(R.id.txtResend);
        btnOtp = findViewById(R.id.btnOtp);

        Intent myintent = getIntent();
        String email = myintent.getStringExtra("email");


        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra các TextView
                if (isAnyTextViewNull()) {
                    txtWrong.setVisibility(View.VISIBLE); // Hiển thị textview "wrong enter"
                    txtWrong.setTextColor(getResources().getColor(android.R.color.holo_red_dark)); // Đổi màu văn bản thành màu đỏ
                    startResendCountdown();
                } else {
                    showToast("All fields are filled!");
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
                            // Xử lý kết quả ở đây
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
    }

    // Kiểm tra xem có bất kỳ TextView nào có giá trị null không
    private boolean isAnyTextViewNull() {
        return otp1.getText().toString().isEmpty() ||
                otp2.getText().toString().isEmpty() ||
                otp3.getText().toString().isEmpty() ||
                otp4.getText().toString().isEmpty() ||
                otp5.getText().toString().isEmpty() ||
                otp6.getText().toString().isEmpty();
    }

    // Bắt đầu đếm ngược cho việc gửi lại OTP
    private void startResendCountdown() {
        countDownTimer = new CountDownTimer(60000, 1000) { // 60 giây, mỗi lần giảm 1 giây
            @Override
            public void onTick(long millisUntilFinished) {
                txtResend.setTextColor(getResources().getColor(android.R.color.holo_red_dark)); // Đổi màu văn bản thành màu đỏ

                txtResend.setText("Resend in " + millisUntilFinished / 1000 + "s"); // Hiển thị số giây còn lại
            }

            @Override
            public void onFinish() {
                txtResend.setTextColor(getResources().getColor(android.R.color.black)); // Đổi màu văn bản về màu đen khi kết thúc
                txtResend.setText("Resend"); // Hiển thị lại văn bản "Resend"
            }
        }.start();
    }

    // Phương thức hiển thị thông báo toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Dừng đếm ngược nếu activity bị hủy
        }
    }

}
