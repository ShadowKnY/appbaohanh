package com.example.myapplication.add_edit_delete;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.domain.PopularDomain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

public class editDeleteActivity extends AppCompatActivity {

    private EditText titleTxt, descriptionTxt, priceTxt, picUrlTxt, reviewTxt, scoreTxt, numberInChartTxt;
    private Button saveBtn, deleteBtn;
    private DatabaseReference databaseRef;
    private String category;
    private String title; // Changed from productId to title

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        // Ánh xạ các view
        titleTxt = findViewById(R.id.title_txt);
        descriptionTxt = findViewById(R.id.description_txt);
        priceTxt = findViewById(R.id.price_txt);
        picUrlTxt = findViewById(R.id.picUrl_txt);
        reviewTxt = findViewById(R.id.review_txt);
        scoreTxt = findViewById(R.id.score_txt);
        numberInChartTxt = findViewById(R.id.numberInChart_txt);
        saveBtn = findViewById(R.id.saveBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        // Khởi tạo DatabaseReference
        databaseRef = FirebaseDatabase.getInstance().getReference().child("Product");

        // Lấy thông tin sản phẩm từ Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category = getIntent().getStringExtra("category");
            title = getIntent().getStringExtra("title"); // Changed from productId to title

            // Hiển thị thông tin sản phẩm trong EditText
            titleTxt.setText(extras.getString("title"));
            descriptionTxt.setText(extras.getString("description"));
            priceTxt.setText(extras.getString("price"));
            picUrlTxt.setText(extras.getString("picUrl"));
            reviewTxt.setText(extras.getString("review"));
            scoreTxt.setText(extras.getString("score"));
            numberInChartTxt.setText(extras.getString("numberInChart"));
        } else {
            // Nếu không nhận được dữ liệu, có thể hiển thị thông báo lỗi
            Toast.makeText(this, "Không nhận được thông tin sản phẩm", Toast.LENGTH_SHORT).show();
            finish(); // Kết thúc activity hiện tại
        }

        // Xử lý sự kiện của nút "Lưu"
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin đã chỉnh sửa từ các trường EditText
                String title = titleTxt.getText().toString().trim();
                String description = descriptionTxt.getText().toString().trim();
                String priceStr = priceTxt.getText().toString().trim();
                String picUrl = picUrlTxt.getText().toString().trim();
                String reviewStr = reviewTxt.getText().toString().trim();
                String scoreStr = scoreTxt.getText().toString().trim();
                String numberInChartStr = numberInChartTxt.getText().toString().trim();

                if (title.isEmpty() || picUrl.isEmpty() || reviewStr.isEmpty() || scoreStr.isEmpty() || numberInChartStr.isEmpty() || priceStr.isEmpty() || description.isEmpty()) {
                    Toast.makeText(editDeleteActivity.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    int review = Integer.parseInt(reviewStr);
                    double score = Double.parseDouble(scoreStr);
                    int numberInChart = Integer.parseInt(numberInChartStr);
                    double price = Double.parseDouble(priceStr);

                    // Lưu thông tin đã chỉnh sửa vào Firebase Database
                    String category = "iPhone"; // Thay thế bằng biến chứa tên danh mục sản phẩm

                    DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Product").child(category).child(title);
                    PopularDomain product = new PopularDomain(title, picUrl, review, score, numberInChart, price, description);

                    productRef.setValue(product).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(editDeleteActivity.this, "Sản phẩm đã được cập nhật", Toast.LENGTH_SHORT).show();
                            finish(); // Kết thúc activity sau khi cập nhật thành công
                        } else {
                            Toast.makeText(editDeleteActivity.this, "Đã xảy ra lỗi. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Xác nhận việc xóa sản phẩm
                AlertDialog.Builder builder = new AlertDialog.Builder(editDeleteActivity.this);
                builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                        .setPositiveButton("Có", (dialog, which) -> {
// Thực hiện xóa sản phẩm khỏi cơ sở dữ liệu Firebase
                            deleteProductFromFirebase(category, title);
                        })
                        .setNegativeButton("Không", null)
                        .show();
            }
        });
    }

    private void deleteProductFromFirebase(String category, String title) {
        if (category == null) {
            category = "iPhone"; // Giá trị mặc định cho category khi không có giá trị được truyền vào từ bên ngoài
        }

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Product").child(category);

        Query query = productRef.orderByChild("title").equalTo(title);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snapshot.getRef().removeValue().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(editDeleteActivity.this, "Sản phẩm đã được xóa", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(editDeleteActivity.this, "Đã xảy ra lỗi. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(editDeleteActivity.this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(editDeleteActivity.this, "Đã xảy ra lỗi. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}