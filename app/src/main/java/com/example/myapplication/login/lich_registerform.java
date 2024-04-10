package com.example.myapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.SplashScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.checkerframework.checker.nullness.qual.NonNull;

public class lich_registerform extends AppCompatActivity {

        EditText editTextUsername;
        EditText editTextPassword, retypePassWord;
        EditText textEmail;
        Button registerButton;
        FirebaseAuth mAuth;
        ProgressBar progressBar;
        Button forgotButton;
        TextView textView;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lich_registerform);

            editTextUsername = findViewById(R.id.username);
            editTextPassword = findViewById(R.id.password);
            retypePassWord = findViewById(R.id.retypepassword);
            textEmail = findViewById(R.id.email);
            registerButton = findViewById(R.id.registerButton);
            mAuth = FirebaseAuth.getInstance();
            progressBar = findViewById(R.id.progressBar);
            textView = findViewById(R.id.LoginNow);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), lich_loginform.class);
                    startActivity(intent);
                    finish();
                }
            });
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    progressBar.setVisibility(View.VISIBLE);

                    String username, password, email;
                    username = editTextUsername.getText().toString();
                    password = editTextPassword.getText().toString();
                    email = textEmail.getText().toString();

                    if (TextUtils.isEmpty(email)){
                        Toast.makeText(lich_registerform.this, "Enter Email or Username", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)){
                        Toast.makeText(lich_registerform.this, "Enter Password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!password.equals(retypePassWord.getText().toString())) {
                        Toast.makeText(lich_registerform.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    progressBar.setVisibility(View.GONE);

                                    if (task.isSuccessful()) {
                                        Toast.makeText(lich_registerform.this, "Accout created",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(lich_registerform.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            });

            forgotButton =findViewById(R.id.forgotButton);
            forgotButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(lich_registerform.this, ForgotActivity.class);
                    startActivity(i);
                }


            });

        }
    }
