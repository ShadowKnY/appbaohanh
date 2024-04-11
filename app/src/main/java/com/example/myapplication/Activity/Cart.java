package com.example.myapplication.Activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.Adapter.CartAdapter;
import com.example.myapplication.R;
import com.example.myapplication.domain.PopularDomain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    //khởi tạo các biến
    ImageView backBtn;
    TextView subtotalTextView,dlvTxt,taxTxt,totalTxt;
    LottieAnimationView animationView;
    RecyclerView recyclerView;
    CartAdapter adapter;
    List<PopularDomain> cartItems;
    DatabaseReference cartRef;

    //hàm onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

         backBtn = findViewById(R.id.backBtn);

        //kill giao diện hiện tại và trở về giao diện trước
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Khởi tạo danh sách cartItems
        cartItems = new ArrayList<>();

        // Khởi tạo và cấu hình Adapter
        recyclerView = findViewById(R.id.cartView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        subtotalTextView = findViewById(R.id.subtotalTextView); // Tìm TextView Subtotal
        taxTxt = findViewById(R.id.taxTxt);
        dlvTxt =findViewById(R.id.dlvTxt);
        totalTxt = findViewById(R.id.totalTxt);
        adapter = new CartAdapter(cartItems,subtotalTextView,dlvTxt,taxTxt,totalTxt);
        recyclerView.setAdapter(adapter);

        // Thực hiện truy vấn Firebase để lấy dữ liệu giỏ hàng
        // khởi tạo và cấu hình Firebase Database
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userId = user.getUid();

        //cartRef sẽ trỏ đến các userID trong nút cart
        cartRef = FirebaseDatabase.getInstance().getReference("carts").child(userId);
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cartItems.clear(); // Xóa danh sách cũ trước khi cập nhật mới
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PopularDomain cartItem = snapshot.getValue(PopularDomain.class);
                    cartItems.add(cartItem); // Thêm dữ liệu từ Firebase vào danh sách
                }
                adapter.notifyDataSetChanged(); // Cập nhật giao diện sau khi có dữ liệu mới
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });

        //animation thanh toán
        animationView = new LottieAnimationView(this);
        animationView.setAnimation(R.raw.faceid); // Replace with your Lottie animation file
        animationView.setRepeatCount(ValueAnimator.INFINITE);
        animationView.playAnimation();

        // Get reference to "Order Now" button
        Button orderNowButton = findViewById(R.id.OrderNowbtn);
        orderNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrderPopup();
            }
        });
    }


    // hàm show animation khi ấn thanh toán
    public void showOrderPopup() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(R.layout.pop_up, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(popupView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
