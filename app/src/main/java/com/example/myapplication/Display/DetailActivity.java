package com.example.myapplication.Display;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.add_edit_delete.editDeleteActivity;
import com.example.myapplication.domain.PopularDomain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Calendar;

public class DetailActivity extends AppCompatActivity {
    ImageView backBtn;
    TextView titleTxt, priceTxt, reviewTxt, ratingTxt, descriptionTxt, numberInChartTxt;
    ImageView itemPic;
    Button editBtn;
    String category;
    PopularDomain productData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        category = getIntent().getStringExtra("category");

        editBtn = findViewById(R.id.editBtn);
        numberInChartTxt = findViewById(R.id.numberInChartDetail);
        titleTxt = findViewById(R.id.titleDetail);
        priceTxt = findViewById(R.id.priceDetail);
        reviewTxt = findViewById(R.id.reviewDetail);
        ratingTxt = findViewById(R.id.ratingDetail);
        descriptionTxt = findViewById(R.id.descriptionDetail);
        backBtn = findViewById(R.id.backBtn);
        itemPic = findViewById(R.id.itemPic);

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
            // Picasso.get().load(getIntent().getStringExtra("itemPic"))
            //         .placeholder(R.drawable.grey_background)
            //         .into(itemPic);
            // titleTxt.setText(getIntent().getStringExtra("titleDetail"));
            // priceTxt.setText(String.valueOf(getIntent().getDoubleExtra("priceDetail", 0.0)));
            // reviewTxt.setText(String.valueOf(getIntent().getIntExtra("reviewDetail",0)));
            // ratingTxt.setText(String.valueOf(getIntent().getDoubleExtra("ratingDetail", 0.0)));
            // numberInChartTxt.setText(String.valueOf(getIntent().getIntExtra("numberInChartDetail", 0)));
            // descriptionTxt.setText(getIntent().getStringExtra("descriptionDetail"));
        }


        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedToCart();
            }
        });
        cartDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Cart.class);
                startActivity(intent);
                finish();
            }
        });
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

//                intent.putExtra("category", category);
//
//                // Đặt các thông tin sản phẩm vào Intent
//                intent.putExtra("category", category);
//                intent.putExtra("title", titleTxt.getText().toString());
//                intent.putExtra("price", priceTxt.getText().toString());
//                intent.putExtra("review", reviewTxt.getText().toString());
//                intent.putExtra("score", ratingTxt.getText().toString());
//                intent.putExtra("description", descriptionTxt.getText().toString());
//                intent.putExtra("numberInChart", numberInChartTxt.getText().toString());
//                intent.putExtra("picUrl", getIntent().getStringExtra("itemPic"));

                // Chuyển sang activity tương ứng
                startActivity(intent);
            }
        });

    }

    private void addedToCart() {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = firebaseAuth.getCurrentUser();

            // Kiểm tra xem người dùng đã đăng nhập chưa
            if (user == null) {
                // Nếu người dùng chưa đăng nhập, bạn có thể chuyển hướng người dùng đến màn hình đăng nhập hoặc hiển thị thông báo lỗi.
               Toast.makeText(this, "Vui lòng đăng nhập trước khi thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                return;
            }

            String userId = user.getUid();

            // Lấy thông tin sản phẩm từ Intent
            String title = getIntent().getStringExtra("titleDetail");
            double price = getIntent().getDoubleExtra("priceDetail", 0.0);
            String imageUrl = getIntent().getStringExtra("itemPic");

            // Tạo đối tượng sản phẩm
            PopularDomain product = new PopularDomain(title, imageUrl, price);

            // Lấy thời gian hiện tại
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1; // Month start from 0
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            int second = cal.get(Calendar.SECOND);

            // Tạo chuỗi ngày tháng để làm key cho sản phẩm trong giỏ hàng
            String cartItemId = year + "-" + month + "-" + day + "_" + hour + "-" + minute + "-" + second;

            product.setItemId(cartItemId);
        // Thêm sản phẩm vào giỏ hàng trên Firebase Realtime Database
            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("carts").child(userId);
            PopularDomain productAdd = new PopularDomain(product.getTitle(),product.getPicUrl(), product.getReview(),product.getScore(),product.getNumberInChart(), product.getPrice(), product.getDecription(),cartItemId);
            cartRef.child(cartItemId).setValue(product)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Xử lý thành công: Hiển thị thông báo hoặc cập nhật giao diện người dùng
                            Toast.makeText(DetailActivity.this, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Xử lý lỗi: Hiển thị thông báo hoặc cập nhật giao diện người dùng với thông báo lỗi
                           Toast.makeText(DetailActivity.this, "Lỗi: Không thể thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    }
