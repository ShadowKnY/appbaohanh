package com.example.myapplication.info;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.R;
//import com.example.myapplication.login.change_password;
import com.example.myapplication.login.change_password;
import com.example.myapplication.login.lich_loginform;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Calendar;

public class info extends AppCompatActivity {
    private static final int INFO_ACTIVITY_REQUEST_CODE = 1;
    private TextView textViewUsername;
    private TextView textViewPhone;
    private TextView textViewTuoi;
    private TextView textViewGt;
    private TextView textViewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Button backButton = findViewById(R.id.back_button);
        Button dmkButoon = findViewById(R.id.dmk_button);
        Button deleteButton = findViewById(R.id.delete_button);

        textViewUsername = findViewById(R.id.textViewUsername);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewTuoi = findViewById(R.id.textViewTuoi);
        textViewGt = findViewById(R.id.textViewGt);
        textViewDate = findViewById(R.id.textViewDate);

        textViewUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(info.this, changename.class);
                startActivity(intent);
            }
        });

        textViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(info.this, changephone.class);
                startActivity(intent);
            }
        });
        textViewTuoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(info.this, changetuoi.class);
                startActivity(intent);
            }
        });
        final String[] options = {"Nam", "Nữ", "Khác"};
        final TextView textViewGt = findViewById(R.id.textViewGt);

        textViewGt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(info.this);
                builder.setTitle("Chọn giới tính");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedOption = options[which];
                        textViewGt.setText(selectedOption);
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                        userRef.child("gioiTinh").setValue(selectedOption)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(info.this, "Lưu giới tính thành công", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(info.this, "Lưu giới tính thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                builder.show();
            }
        });

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(info.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dmkButoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(info.this, change_password.class);
                startActivity(intent);
                finish();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });

        //Kết nối đến Firebase
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // Tạo người dùng mới với id là userId
                    infouser newUser = new infouser("Username", "Phone", "Tuoi", "GioiTinh", "NgaySinh");
                    ref.child(userId).setValue(newUser);
                } else {
                    String username = dataSnapshot.child("username").getValue(String.class);
                    String phone = dataSnapshot.child("phone").getValue(String.class);
                    String tuoi = dataSnapshot.child("tuoi").getValue(String.class);
                    String gt = dataSnapshot.child("gioiTinh").getValue(String.class);
                    String date = dataSnapshot.child("ngaySinh").getValue(String.class);

                    // Đẩy dữ liệu vào TextView tương ứng
                    textViewUsername.setText(username);
                    textViewPhone.setText(phone);
                    textViewTuoi.setText(tuoi);
                    textViewGt.setText(gt);
                    textViewDate.setText(date);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });




    }
    public void logoutClick(View view) {
        logout();
    }
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        // Điều hướng đến màn hình đăng nhập hoặc màn hình khác tùy theo thiết kế của ứng dụng của bạn.
        Intent intent = new Intent(info.this, lich_loginform.class);
        startActivity(intent);
        finish();
    }
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Lưu ngày sinh vào Firebase
                saveBirthDateToFirebase(year, month, dayOfMonth);
            }
        }, year, month, day);

        datePickerDialog.show();
    }
    private void saveBirthDateToFirebase(int year, int month, int dayOfMonth) {
        // Thực hiện lưu ngày sinh vào Firebase ở đây
        String birthDate = dayOfMonth + "/" + (month + 1) + "/" + year;

        // Ví dụ: Lưu ngày sinh vào nút user của Firebase Realtime Database
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();
        databaseRef.child("users").child(userId).child("ngaySinh").setValue(birthDate);
        loadInfoForm();
    }
    private void loadInfoForm() {
        Intent intent = new Intent(info.this, info.class);
        startActivityForResult(intent, INFO_ACTIVITY_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INFO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            loadInfoForm();
        }
    }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa tài khoản");
        builder.setMessage("Bạn có chắc chắn muốn xóa tài khoản không?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Thực hiện xóa tài khoản từ Firebase
                deleteAccountFromFirebase();
                finish();
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
    private void deleteAccountFromFirebase() {
        // Kết nối đến Firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();

        // Xóa tài khoản người dùng từ Firebase Authentication
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Xóa tài khoản người dùng từ Firebase Realtime Database
                    ref.child("users").child(userId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Xóa thành công
                                // Điều hướng đến màn hình đăng nhập hoặc màn hình khác tùy theo thiết kế của ứng dụng của bạn.
                                Intent intent = new Intent(info.this, lich_loginform.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Xảy ra lỗi khi xóa tài khoản từ Firebase Realtime Database
                                Toast.makeText(info.this, "Lỗi xóa tài khoản từ Firebase Realtime Database", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // Xảy ra lỗi khi xóa tài khoản từ Firebase Authentication
                    Toast.makeText(info.this, "Lỗi xóa tài khoản từ Firebase Authentication", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}