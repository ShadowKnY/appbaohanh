package com.example.myapplication.Display;

import android.os.Bundle;
import android.widget.EditText;
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
    private EditText edtTitle,edtPrice,edtScr,edtReview;
    private RecyclerView rcvProduct;
    private displayAdapter mdisplayAdapter;
    private List<PopularDomain> mlistProduct;
    private String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        initUI();

//        Intent intent = getIntent();
//        if (intent != null) {
//            category = intent.getStringExtra("category");
//            // Kiểm tra xem category có giá trị hay không
//            if (category != null && !category.isEmpty()) {
//                // Gọi phương thức để lấy danh sách sản phẩm từ Firebase dựa trên category
//                getListFromRealTimeDb(category);
//            }
//        }


        getListFromRealTimeDb();


    }
    private void initUI(){
//        edtTitle = findViewById(R.id.titleTxt);
//        edtPrice = findViewById(R.id.feeTxt);
        rcvProduct = findViewById(R.id.ipadView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        rcvProduct.setLayoutManager(gridLayoutManager);

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL);
//        rcvProduct.addItemDecoration(dividerItemDecoration);
        mlistProduct = new ArrayList<>();
        mdisplayAdapter = new displayAdapter(mlistProduct);
        rcvProduct.setAdapter(mdisplayAdapter);
    }
    private void getListFromRealTimeDb(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myProd = database.getReference("Product").child("iPhone");
        myProd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mlistProduct.clear();
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    // Lặp qua tất cả các sản phẩm trong danh mục "iPhone"
                    for (DataSnapshot itemSnapshot : productSnapshot.getChildren()) {
                        PopularDomain product = itemSnapshot.getValue(PopularDomain.class);
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
