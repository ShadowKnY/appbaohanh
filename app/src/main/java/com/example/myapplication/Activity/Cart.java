package com.example.myapplication.Activity;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
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

    // Declare variables
    private ImageView backBtn;
    private TextView subtotalTextView, dlvTxt, taxTxt, totalTxt;
    private LottieAnimationView animationView;
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<PopularDomain> cartItems;
    private DatabaseReference cartRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Get userId from Intent
        String userId = getIntent().getStringExtra("userId");

        // Check if userId is valid
        if (userId == null || userId.isEmpty()) {
            // Handle invalid userId, for example, show an error message and return
            return;
        }

        // Initialize UI components
        initView();

        // Set onClickListener for back button
        initBackButton();

        // Initialize RecyclerView and Adapter
        initRecyclerView(userId);

        // Initialize Firebase Database
        initFirebase(userId);

        // Initialize and show animation
        initAnimation();

        // Set onClickListener for "Order Now" button
        initOrderNowButton();
    }

    // Initialize UI components
    private void initView() {
        backBtn = findViewById(R.id.backBtn);
        subtotalTextView = findViewById(R.id.subtotalTextView);
        taxTxt = findViewById(R.id.taxTxt);
        dlvTxt = findViewById(R.id.dlvTxt);
        totalTxt = findViewById(R.id.totalTxt);
    }

    // Set onClickListener for back button
    private void initBackButton() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Initialize RecyclerView and Adapter
    private void initRecyclerView(String userId) {
        recyclerView = findViewById(R.id.cartView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartItems = new ArrayList<>();
        adapter = new CartAdapter(cartItems, subtotalTextView, dlvTxt, taxTxt, totalTxt);
        recyclerView.setAdapter(adapter);
    }

    // Initialize Firebase Database
    private void initFirebase(String userId) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // Get reference to user's cart in Firebase Database
            cartRef = FirebaseDatabase.getInstance().getReference("carts").child(userId);
            // Add ValueEventListener to fetch cart items
            cartRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    cartItems.clear(); // Clear old cart items
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PopularDomain cartItem = snapshot.getValue(PopularDomain.class);
                        cartItems.add(cartItem); // Add data from Firebase to list
                    }
                    adapter.notifyDataSetChanged(); // Update UI with new data
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle error if needed
                }
            });
        }
    }

    // Initialize and show animation
    private void initAnimation() {
        animationView = new LottieAnimationView(this);
        animationView.setAnimation(R.raw.faceid);
        animationView.setRepeatCount(ValueAnimator.INFINITE);
        animationView.playAnimation();
    }

    // Set onClickListener for "Order Now" button
    private void initOrderNowButton() {
        Button orderNowButton = findViewById(R.id.OrderNowbtn);
        orderNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrderPopup();
            }
        });
    }

    // Show order popup
    public void showOrderPopup() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(R.layout.pop_up, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(popupView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle OK button click
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle Cancel button click
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
