package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Activity.MainActivity;

public class LoginTest extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);
        btn = findViewById(R.id.test);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v){
                    Intent i = new Intent(getApplicationContext(), SplashScreen.class);
                    startActivity(i);
                }
            });
    }
}
