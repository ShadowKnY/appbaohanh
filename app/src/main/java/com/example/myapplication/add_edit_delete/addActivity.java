package com.example.myapplication.add_edit_delete;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.domain.PopularDomain;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addActivity extends AppCompatActivity {

    private EditText title, description, price, picUrl, review, score, numberIC;
    private Button add_btn;
    private DatabaseReference databaseRef;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Khởi tạo DatabaseReference
        databaseRef = FirebaseDatabase.getInstance().getReference("products");

        // Ánh xạ các view
        title = findViewById(R.id.title_txt);
        description = findViewById(R.id.description_txt);
        price = findViewById(R.id.price_txt);
        picUrl = findViewById(R.id.picUrl_txt);
        review = findViewById(R.id.review_txt);
        score = findViewById(R.id.score_txt);
        numberIC = findViewById(R.id.numberInChart_txt);
        add_btn = findViewById(R.id.add_btn);

        // Lấy loại sản phẩm từ intent hoặc từ biến truyền vào
        category = getIntent().getStringExtra("category");

        // Thêm sản phẩm khi nhấn nút "Thêm"
        findViewById(R.id.add_btn).setOnClickListener(v -> {
            String titleStr = title.getText().toString().trim();
            String picUrlStr = picUrl.getText().toString().trim();
            String reviewStr = review.getText().toString().trim();
            String scoreStr = score.getText().toString().trim();
            String numberICStr = numberIC.getText().toString().trim();
            String priceStr = price.getText().toString().trim();
            String descriptionStr = description.getText().toString().trim();

            // Kiểm tra xem các trường input có rỗng hay không
            if (titleStr.isEmpty() || picUrlStr.isEmpty() || reviewStr.isEmpty() || scoreStr.isEmpty() || numberICStr.isEmpty() || priceStr.isEmpty() || descriptionStr.isEmpty()) {
                // Hiển thị thông báo lỗi nếu có trường nào đó bị trống
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                // Parse dữ liệu nhập vào từ các EditText sang các kiểu tương ứng
                int review = Integer.parseInt(reviewStr);
                double score = Double.parseDouble(scoreStr);
                int numberInChart = Integer.parseInt(numberICStr);
                double price = Double.parseDouble(priceStr);

                // Thêm sản phẩm vào cơ sở dữ liệu Firebase
                addProductToFirebase(titleStr, picUrlStr, review, score, numberInChart, price, descriptionStr);
            }
        });
    }

    private void addProductToFirebase(String title, String picUrl, int review, double score, int numberInChart, double price, String description) {
        // Thêm sản phẩm vào cơ sở dữ liệu Firebase
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Product/" + category);
        String productId = databaseRef.push().getKey(); // Tạo một ID mới cho sản phẩm
        PopularDomain product = new PopularDomain(title, picUrl, review, score, numberInChart, price, description);
        databaseRef.child(productId).setValue(product).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Hiển thị thông báo khi sản phẩm được thêm thành công
                Toast.makeText(this, "Sản phẩm đã được thêm vào " + category, Toast.LENGTH_SHORT).show();
                finish(); // Đóng activity sau khi thêm sản phẩm thành công
            } else {
                // Hiển thị thông báo khi có lỗi xảy ra khi thêm sản phẩm
                Toast.makeText(this, "Đã xảy ra lỗi. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
