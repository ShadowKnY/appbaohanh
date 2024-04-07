package com.example.myapplication.Display;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.displayAdapter;
import com.example.myapplication.R;
import com.example.myapplication.domain.PopularDomain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IpadDisplay extends AppCompatActivity {

    private RecyclerView rcvProduct;
    private displayAdapter mdisplayAdapter;
    private List<PopularDomain> mlistProduct;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        initUI();

        Intent intent = getIntent();
        if (intent != null) {
            category = intent.getStringExtra("category");
            if (category != null && !category.isEmpty()) {
                getListFromRealTimeDb(category);
            }
        }
    }

    private void initUI() {
        rcvProduct = findViewById(R.id.ipadView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvProduct.setLayoutManager(gridLayoutManager);

        mlistProduct = new ArrayList<>();
        mdisplayAdapter = new displayAdapter(mlistProduct);
        rcvProduct.setAdapter(mdisplayAdapter);
    }

    private void getListFromRealTimeDb(String category) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myProd = database.getReference("Product").child(category);
        myProd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mlistProduct.clear();
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    PopularDomain product = productSnapshot.getValue(PopularDomain.class);
                    mlistProduct.add(product);
                }
                mdisplayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(IpadDisplay.this, "Không thể lấy dữ liệu từ cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

