package info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import info.infouser;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class info extends AppCompatActivity {
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(info.this, MainActivity.class);
                startActivity(intent);
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
}