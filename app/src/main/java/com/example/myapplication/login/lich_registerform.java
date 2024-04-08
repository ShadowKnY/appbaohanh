package com.example.myapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.SplashScreen;

public class lich_registerform extends AppCompatActivity {

        EditText username;
        EditText password;
        EditText textEmail;
        Button registerButton;
        Button forgotButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lich_registerform);

            username = findViewById(R.id.username);
            password = findViewById(R.id.password);
            textEmail = findViewById(R.id.email);
            registerButton = findViewById(R.id.registerButton);
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), SplashScreen.class);
                    startActivity(i);
                }
            });
        }
    }
