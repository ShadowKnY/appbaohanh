package info;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class changename extends AppCompatActivity {
    private static final int INFO_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changename);

        Button saveButton = findViewById(R.id.buttonSave);
        EditText editTextNewName = findViewById(R.id.editTextNewName);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editTextNewName.getText().toString().trim();
                if (!newName.isEmpty()) {
                    updateUsernameInFirebase(newName);
                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập tên mới", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void updateUsernameInFirebase(String newName) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(userId);
        ref.child("username").setValue(newName)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Đã cập nhật tên thành công", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(changename.this, info.class);
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