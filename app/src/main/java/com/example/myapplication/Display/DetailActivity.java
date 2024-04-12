package com.example.myapplication.Display;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myapplication.Activity.Cart;
import com.example.myapplication.R;
import com.example.myapplication.add_edit_delete.editDeleteActivity;
import com.example.myapplication.domain.PopularDomain;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class DetailActivity extends AppCompatActivity {
    ImageView backBtn, cartDetailBtn;
    TextView titleTxt, priceTxt, reviewTxt, ratingTxt, descriptionTxt, numberInChartTxt;
    ImageView itemPic;
    AppCompatButton addToCartBtn,editBtn;
    DatabaseReference mDatabase;
    PopularDomain productData;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        titleTxt = findViewById(R.id.titleDetail);
        priceTxt = findViewById(R.id.priceDetail);
        reviewTxt = findViewById(R.id.reviewDetail);
        ratingTxt = findViewById(R.id.ratingDetail);
        descriptionTxt = findViewById(R.id.descriptionDetail);
        backBtn = findViewById(R.id.backBtn);
        itemPic = findViewById(R.id.itemPic);
        editBtn = findViewById(R.id.editBtn);
        numberInChartTxt = findViewById(R.id.numberInChartDetail);
        addToCartBtn = findViewById(R.id.buyBtn);
        cartDetailBtn = findViewById(R.id.cartDetailBtn);

        // Lấy dữ liệu sản phẩm từ Intent và hiển thị lên giao diện
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String productString = bundle.getString("DataProduct");
            Gson gson = new Gson();
            productData = gson.fromJson(productString, PopularDomain.class);
            Picasso.get().load(productData.getPicUrl())
                    .placeholder(R.drawable.grey_background)
                    .into(itemPic);
            titleTxt.setText(productData.getTitle());
            priceTxt.setText(String.valueOf(productData.getPrice()));
            reviewTxt.setText(String.valueOf(productData.getReview()));
            ratingTxt.setText(String.valueOf(productData.getScore()));
            numberInChartTxt.setText(String.valueOf(productData.getNumberInChart()));
            descriptionTxt.setText(productData.getDecription());
        }

        // Xử lý sự kiện khi nhấn vào nút "Thêm vào giỏ hàng"
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedToCart();
            }
        });

        // Xử lý sự kiện khi nhấn vào nút "Giỏ hàng"
        // Xử lý sự kiện khi nhấn vào nút "Giỏ hàng"
        cartDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Cart.class);
                // Chuyển dữ liệu userId qua Intent
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
            }
        });


        // Xử lý sự kiện khi nhấn vào nút "Quay lại"
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, editDeleteActivity.class);
                intent.putExtra("DataProduct", productData.toString());
                startActivity(intent);
            }
        });
    }

    // Phương thức thêm sản phẩm vào giỏ hàng
    private void addedToCart() {
        // Lấy thông tin người dùng hiện tại từ Firebase Authentication


        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (user == null) {
            // Nếu chưa đăng nhập, hiển thị thông báo và kết thúc phương thức
            Toast.makeText(this, "Vui lòng đăng nhập trước khi thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy ID người dùng hiện tại


        // Lấy thông tin sản phẩm từ đối tượng productData
        String title = productData.getTitle();
        double price = productData.getPrice();
        String imageUrl = productData.getPicUrl();

        // Tạo đối tượng sản phẩm
        PopularDomain product = new PopularDomain(title, imageUrl, price);

        // Lấy thời gian hiện tại
        Calendar cal = Calendar.getInstance();
        String cartItemId = String.valueOf(cal.getTimeInMillis());

        // Đặt itemId cho sản phẩm
        product.setItemId(cartItemId);

        // Thêm sản phẩm vào giỏ hàng trên Firebase Realtime Database
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("carts").child(userId);
        cartRef.child(cartItemId).setValue(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Thành công: Hiển thị thông báo và kết thúc phương thức
                        Toast.makeText(DetailActivity.this, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Lỗi: Hiển thị thông báo lỗi
                        Toast.makeText(DetailActivity.this, "Lỗi: Không thể thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
