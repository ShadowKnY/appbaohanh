package info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.R;
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
    private TextView textViewNew;

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
        textViewNew = findViewById(R.id.textViewNew);

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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(info.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Kết nối đến Firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Đây là phương thức được gọi mỗi khi dữ liệu trong tham chiếu thay đổi

                // Lấy giá trị của dữ liệu từ DataSnapshot
                String username = dataSnapshot.child("username").getValue(String.class);
                String phone = dataSnapshot.child("phone").getValue(String.class);
                String tuoi = dataSnapshot.child("tuoi").getValue(String.class);
                String gt = dataSnapshot.child("gioitinh").getValue(String.class);
                String date = dataSnapshot.child("ngaysinh").getValue(String.class);
                String newInfo = dataSnapshot.child("newInfo").getValue(String.class);

                // Đẩy dữ liệu vào TextView tương ứng
                textViewUsername.setText(username);
                textViewPhone.setText(phone);
                textViewTuoi.setText(tuoi);
                textViewGt.setText(gt);
                textViewDate.setText(date);
                textViewNew.setText(newInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình lấy dữ liệu từ Firebase
            }
        });

    }
}