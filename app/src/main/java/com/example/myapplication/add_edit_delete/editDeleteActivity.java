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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editDeleteActivity extends AppCompatActivity {

    private EditText titleTxt, descriptionTxt, priceTxt, picUrlTxt, reviewTxt, scoreTxt, numberInChartTxt;
    private Button saveBtn, deleteBtn;
    private DatabaseReference databaseRef;
    private String category, productId;

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
//            productId = getIntent().getStringExtra("productId");
//            category = getIntent().getStringExtra("category");
//
//            if (productId == null || category == null) {
//                Log.e("EditDeleteActivity", "ProductId or category is null");
//                // Xử lý một cách phù hợp khi có lỗi xảy ra
//            } else {
//                Log.e("EditDeleteActivity", "Intent extras is null");
//                // Xử lý một cách phù hợp khi có lỗi xảy ra
//            }
            category = getIntent().getStringExtra("category");

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
                // Lưu thông tin đã chỉnh sửa vào Firebase Database
                String title = titleTxt.getText().toString().trim();
                String description = descriptionTxt.getText().toString().trim();
                String priceStr = priceTxt.getText().toString().trim();
                String picUrl = picUrlTxt.getText().toString().trim();
                String reviewStr = reviewTxt.getText().toString().trim();
                String scoreStr = scoreTxt.getText().toString().trim();
                String numberInChartStr = numberInChartTxt.getText().toString().trim();

                if (title.isEmpty() || picUrl.isEmpty() || reviewStr.isEmpty() || scoreStr.isEmpty() || numberInChartStr.isEmpty() || priceStr.isEmpty() || description.isEmpty()) {
                    Toast.makeText(editDeleteActivity.this, "Hãy điền đầy đủ", Toast.LENGTH_SHORT).show();
                } else {

                    int review = Integer.parseInt(reviewStr);
                    double score = Double.parseDouble(scoreStr);
                    int numberInChart = Integer.parseInt(numberInChartStr);
                    double price = Double.parseDouble(priceStr);

                    editProductInDataBase(category, productId, title, picUrl, review, score, numberInChart, price, description);
                }
            }
        });

        findViewById(R.id.deleteBtn).setOnClickListener(v -> {
            // Xác nhận việc xóa sản phẩm
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        // Thực hiện xóa sản phẩm khỏi cơ sở dữ liệu Firebase
                        deleteProductFromFirebase();
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });
    }

    private void editProductInDataBase(String category, String productId, String title, String picUrl, int review, double score, int numberInChart, double price, String description) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Product/" + category);
        PopularDomain product = new PopularDomain(title, picUrl, review, score, numberInChart, price, description);
        databaseRef.child(productId).setValue(product).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(editDeleteActivity.this, "Sản phẩm đã được cập nhật", Toast.LENGTH_SHORT).show();
                finish(); // Kết thúc activity sau khi sửa sản phẩm thành công
            } else {
                Toast.makeText(editDeleteActivity.this, "Đã xảy ra lỗi. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức xóa sản phẩm khỏi cơ sở dữ liệu Firebase
    private void deleteProductFromFirebase() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Product/" + category);
        databaseRef.child(productId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(editDeleteActivity.this, "Sản phẩm đã được xóa", Toast.LENGTH_SHORT).show();
                finish(); // Kết thúc activity sau khi xóa sản phẩm thành công
            } else {
                Toast.makeText(editDeleteActivity.this, "Đã xảy ra lỗi. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}



//    // Phương thức để lưu các thay đổi của sản phẩm vào Firebase
//    private void saveProductChanges() {
//        // Lấy các giá trị từ các EditText
//        String titleStr = titleTxt.getText().toString().trim();
//        String picUrlStr = picUrlTxt.getText().toString().trim();
//        String reviewStr = reviewTxt.getText().toString().trim();
//        String scoreStr = scoreTxt.getText().toString().trim();
//        String numberICStr = numberInChartTxt.getText().toString().trim();
//        String priceStr = priceTxt.getText().toString().trim();
//        String descriptionStr = descriptionTxt.getText().toString().trim();
//
//        // Tạo đối tượng PopularDomain mới với các giá trị mới
//        PopularDomain updateProduct = new PopularDomain(titleStr, picUrlStr, Integer.parseInt(reviewStr), Double.parseDouble(scoreStr),
//                Integer.parseInt(numberICStr), Double.parseDouble(priceStr), descriptionStr);
//
//        // Cập nhật sản phẩm vào database
//        if (category != null && productId != null) {
//            databaseRef.child(category).child(productId).setValue(updateProduct)
//                    .addOnSuccessListener(aVoid -> {
//                        Toast.makeText(editDeleteActivity.this, "Thông tin sản phẩm đã được cập nhật thành công", Toast.LENGTH_SHORT).show();
//                        finish(); // Kết thúc hoạt động sau khi cập nhật thành công
//                    })
//                    .addOnFailureListener(e -> Toast.makeText(editDeleteActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
//        } else {
//            Log.e("editDeleteActivity", "Category or productId is null");
//        }
//    }
//
//
//    // Phương thức để xóa sản phẩm khỏi Firebase Realtime Database
//    private void deleteProduct() {
//        if (category != null && productId != null) {
//            // Xóa sản phẩm khỏi Firebase Realtime Database
//            databaseRef.child(category).child(productId).removeValue()
//                    .addOnSuccessListener(aVoid -> {
//                        Toast.makeText(editDeleteActivity.this, "Sản phẩm đã được xóa thành công", Toast.LENGTH_SHORT).show();
//                        finish(); // Kết thúc hoạt động sau khi xóa thành công
//                    })
//                    .addOnFailureListener(e -> Toast.makeText(editDeleteActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
//        } else {
//            Log.e("editDeleteActivity", "Category or productId is null");
//            // Xử lý một cách phù hợp khi có lỗi xảy ra
//        }
//    }


