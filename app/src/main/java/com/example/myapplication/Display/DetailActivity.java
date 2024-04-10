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
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    ImageView backBtn;
    TextView titleTxt, priceTxt, reviewTxt, ratingTxt, descriptionTxt, numberInChartTxt;
    ImageView itemPic;
    Button editBtn;
    String category;

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

        Picasso.get().load(getIntent().getStringExtra("itemPic"))
                .placeholder(R.drawable.grey_background)
                .into(itemPic);
        titleTxt.setText(getIntent().getStringExtra("titleDetail"));
        priceTxt.setText(String.valueOf(getIntent().getDoubleExtra("priceDetail", 0.0)));
        reviewTxt.setText(String.valueOf(getIntent().getIntExtra("reviewDetail",0)));
        ratingTxt.setText(String.valueOf(getIntent().getDoubleExtra("ratingDetail", 0.0)));
        numberInChartTxt.setText(String.valueOf(getIntent().getIntExtra("numberInChartDetail", 0)));
        descriptionTxt.setText(getIntent().getStringExtra("descriptionDetail"));

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
                intent.putExtra("category", category);

                // Đặt các thông tin sản phẩm vào Intent
                intent.putExtra("category", category);
                intent.putExtra("title", titleTxt.getText().toString());
                intent.putExtra("price", priceTxt.getText().toString());
                intent.putExtra("review", reviewTxt.getText().toString());
                intent.putExtra("score", ratingTxt.getText().toString());
                intent.putExtra("description", descriptionTxt.getText().toString());
                intent.putExtra("numberInChart", numberInChartTxt.getText().toString());
                intent.putExtra("picUrl", getIntent().getStringExtra("itemPic"));

                // Chuyển sang activity tương ứng
                startActivity(intent);
            }
        });

    }
}
