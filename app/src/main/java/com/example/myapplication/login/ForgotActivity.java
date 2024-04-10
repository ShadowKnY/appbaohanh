package com.example.myapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

public class ForgotActivity extends AppCompatActivity {

    EditText editTextEmailOrPhone;
    EditText editTextOTP;
    Button sendOTPButton;
    Button verifyOTPButton;
    TextView signupText;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        editTextEmailOrPhone = findViewById(R.id.emailOrPhone);
        sendOTPButton = findViewById(R.id.sendOTPButton);
        editTextOTP = findViewById(R.id.otpCode);
        verifyOTPButton = findViewById(R.id.verifyOTPButton);
        textView = findViewById(R.id.LoginNow);
        signupText = findViewById(R.id.signupText);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), lich_loginform.class);
                startActivity(intent);
                finish();
            }
        });

        signupText = findViewById(R.id.signupText);
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForgotActivity.this, lich_registerform.class);
                startActivity(i);
            }
        });

        sendOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện gửi mã OTP đến email hoặc số điện thoại đã nhập
                String emailOrPhone = editTextEmailOrPhone.getText().toString().trim();
                // Thực hiện logic gửi mã OTP tới email hoặc số điện thoại
                // Sau khi gửi xong, hiển thị trường nhập OTP và nút xác thực OTP
                editTextOTP.setVisibility(View.VISIBLE);
                verifyOTPButton.setVisibility(View.VISIBLE);
            }
        });

        // Xử lý sự kiện khi nhấn nút "Verify OTP"
        verifyOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện xác thực mã OTP đã nhập
                String otp = editTextOTP.getText().toString().trim();
                // Thực hiện logic xác thực mã OTP
                // Nếu OTP đúng, chuyển đến màn hình đổi mật khẩu
                // Nếu OTP sai, thông báo cho người dùng và yêu cầu nhập lại
            }
        });

    }
}