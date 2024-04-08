package info;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

public class changephone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changephone);
        Button buttonsave1 = findViewById(R.id.buttonSavePhone);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonsave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePhoneNumber();
            }
        });

    }

    private void savePhoneNumber() {
        // Lấy số điện thoại mới từ EditText
        EditText editTextNewPhone = findViewById(R.id.editTextNewPhone);
        String newPhone = editTextNewPhone.getText().toString();

        // Lấy người dùng hiện tại từ Firebase Authentication
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Cập nhật số điện thoại người dùng
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(null, newPhone);

            user.updatePhoneNumber(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Cập nhật số điện thoại thành công
                                Toast.makeText(changephone.this, "Lưu số điện thoại thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                // Cập nhật số điện thoại thất bại
                                Toast.makeText(changephone.this, "Lưu số điện thoại thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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