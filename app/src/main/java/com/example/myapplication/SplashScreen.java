package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.Activity.MainActivity;

public class SplashScreen extends AppCompatActivity {
    LottieAnimationView lotie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Gán đối tượng lotie bằng cách sử dụng findViewById()
        lotie = findViewById(R.id.lottie);

        // Thiết lập và chạy animation
        lotie.animate().translationX(0).setDuration(0).setStartDelay(0);

        // Handler để chuyển đổi sang MainActivity sau 5 giây
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        }, 2500); // Chuyển đổi sau 5 giây
        finish();
    }
}
