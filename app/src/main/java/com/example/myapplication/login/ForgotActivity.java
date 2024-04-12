package com.example.myapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider;

public class ForgotActivity extends AppCompatActivity {

    EditText editTextEmailOrPhone;
    EditText editTextOTP;
    Button sendOTPButton;
    Button verifyOTPButton;
    TextView signupText;
    TextView textView;

    FirebaseAuth mAuth;
    String verificationId; // Khai báo biến verificationId ở đây

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

        mAuth = FirebaseAuth.getInstance();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), lich_loginform.class);
                startActivity(intent);
                finish();
            }
        });

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

                if (isPhoneNumber(emailOrPhone)) {
                    // Gửi mã OTP qua số điện thoại
//                    mAuth.verifyPhoneNumber(
//                            emailOrPhone,        // Số điện thoại để nhận mã OTP
//                            60,                 // Thời gian chờ trước khi gửi lại mã OTP (s)
//                            java.util.concurrent.TimeUnit.SECONDS, // Đơn vị thời gian
//                            ForgotActivity.this,  // Activity được gán để xử lý callback
//                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                                @Override
//                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                    // Xác nhận tự động khi nhận được mã OTP
//                                    signInWithPhoneAuthCredential(phoneAuthCredential);
//                                }
//
//                                @Override
//                                public void onVerificationFailed(@NonNull FirebaseException e) {
//                                    // Xử lý khi xác minh thất bại
//                                    Toast.makeText(ForgotActivity.this, "Xác minh thất bại", Toast.LENGTH_SHORT).show();
//                                }
//
//                                @Override
//                                public void onCodeSent(@NonNull String vId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                    // Xử lý khi mã OTP được gửi đi
//                                    Toast.makeText(ForgotActivity.this, "Mã OTP đã được gửi", Toast.LENGTH_SHORT).show();
//                                    verificationId = vId; // Gán giá trị cho biến verificationId
//                                }
//                            });
                } else {
                    // Gửi mã OTP qua email
                    mAuth.sendPasswordResetEmail(emailOrPhone)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Gửi mã OTP qua email thành công
                                        // Hiển thị thông báo link reset password đã được gửi
                                        Toast.makeText(ForgotActivity.this, "Link reset password đã được gửi", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Gửi mã OTP qua email thất bại
                                        Toast.makeText(ForgotActivity.this, "Không thể gửi mã OTP", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        // Xử lý sự kiện khi nhấn nút "Verify OTP"
        verifyOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện xác thực mã OTP đã nhập
                String otp = editTextOTP.getText().toString().trim();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
                signInWithPhoneAuthCredential(credential);
            }
        });
    }

    // Phương thức kiểm tra xem chuỗi có phải là số điện thoại hay không
    private boolean isPhoneNumber(String str) {
        return str.matches("^[+]?[0-9]{10,13}$");
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(ForgotActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Đăng nhập thành công
                            FirebaseUser user = task.getResult().getUser();
                            // Tiếp tục xử lý sau khi đăng nhập thành công
                        } else {
                            // Đăng nhập thất bại
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // Mã OTP không hợp lệ
                                Toast.makeText(ForgotActivity.this, "Mã OTP không hợp lệ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}