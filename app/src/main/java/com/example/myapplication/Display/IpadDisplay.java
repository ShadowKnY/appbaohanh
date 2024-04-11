package com.example.myapplication.Display;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.myapplication.Activity.Cart;
import com.example.myapplication.Adapter.displayAdapter;
import com.example.myapplication.R;
import com.example.myapplication.add_edit_delete.addActivity;
import com.example.myapplication.domain.PopularDomain;
import com.example.myapplication.info.info;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IpadDisplay extends AppCompatActivity {


    LinearLayout menu_cart,menu_in4;
    private RecyclerView rcvProduct;
    private displayAdapter mdisplayAdapter;
    private List<PopularDomain> mlistProduct;
    private ImageView backBtn;
    private String category;
    private ImageView addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
//        menu_cart = findViewById(R.id.menu_cart);
//        menu_in4 = findViewById(R.id.menu_in4);
        addBtn = findViewById(R.id.addCircle);
        backBtn = findViewById(R.id.backBtn);
//        menu_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Cart.class);
//                startActivity(intent);
//            }
//        });
//        menu_in4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), info.class);
//                startActivity(intent);
//            }
//        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IpadDisplay.this, addActivity.class);
                i.putExtra("category", category);
                startActivity(i);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initUI();

        Intent intent = getIntent();
        if (intent != null) {
            category = intent.getStringExtra("category");
            if (category != null && !category.isEmpty()) {
                if (intent.getBooleanExtra("showAllProducts", false)) {
                    showAllProducts();
                } else {
                    getListFromRealTimeDb(category);
                }
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
    private void showAllProducts() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference productRef = database.getReference("Product");
        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mlistProduct.clear();
                for (DataSnapshot categorySnapshot : snapshot.getChildren()) {
                    for (DataSnapshot productSnapshot : categorySnapshot.getChildren()) {
                        PopularDomain product = productSnapshot.getValue(PopularDomain.class);
                        mlistProduct.add(product);
                    }
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
