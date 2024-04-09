package com.example.myapplication.Display;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    ImageView backBtn;
    TextView titleTxt, priceTxt, reviewTxt, ratingTxt, descriptionTxt;
    ImageView itemPic;

    DatabaseReference mDatabase;

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
        Picasso.get().load(getIntent().getStringExtra("itemPic"))
                .placeholder(R.drawable.grey_background)
                .into(itemPic);

        titleTxt.setText(getIntent().getStringExtra("titleDetail"));
        priceTxt.setText(String.valueOf(getIntent().getDoubleExtra("priceDetail", 0.0)));
        reviewTxt.setText(String.valueOf(getIntent().getIntExtra("reviewDetail",0)));
        ratingTxt.setText(String.valueOf(getIntent().getDoubleExtra("ratingDetail", 0.0)));
        descriptionTxt.setText(getIntent().getStringExtra("descriptionDetail"));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}