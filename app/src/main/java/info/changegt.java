package info;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.myapplication.R;

public class changegt extends AppCompatActivity {
    private static final int INFO_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changegt);

        Button buttonsave3 = findViewById(R.id.buttonSaveGt);
        EditText editTextNewGt = findViewById(R.id.editTextNewGt);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonsave3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newGt = editTextNewGt.getText().toString().trim();
                if (!newGt.isEmpty()) {
                    updateGtInFirebase(newGt);
                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập giới tính mới", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void updateGtInFirebase(String newGt) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(userId);
        ref.child("gioiTinh").setValue(newGt)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Đã cập nhật giới tính thành công", Toast.LENGTH_SHORT).show();
                        loadInfoForm();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void loadInfoForm() {
        Intent intent = new Intent(changegt.this, info.class);
        startActivityForResult(intent, INFO_ACTIVITY_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INFO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            loadInfoForm();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}