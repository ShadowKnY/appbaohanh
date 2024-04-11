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
        }


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
}
