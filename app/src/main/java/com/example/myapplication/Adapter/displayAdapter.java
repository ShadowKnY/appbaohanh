package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Display.DetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.domain.PopularDomain;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class displayAdapter extends RecyclerView.Adapter<displayAdapter.ProductViewHolder> {
    private Context context;
    private List<PopularDomain> productList = new ArrayList<>();
    private DatabaseReference mDatabase;

    public displayAdapter(List<PopularDomain> productList) {

        this.productList = productList;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Product");
        loadDataFromFirebase();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pup_list, parent, false);
        context = parent.getContext();
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        PopularDomain product = productList.get(position);
        if (product == null) {
            return;
        }
        holder.scrTxt.setText(String.valueOf(product.getScore()));
        holder.titleTxt.setText(product.getTitle());
        holder.feeTxt.setText(String.valueOf(product.getPrice()));
        holder.reviewTxt.setText(String.valueOf(product.getReview()));
        Glide.with(context)
                .load(product.getPicUrl())
                .into(holder.pic);

        // Thêm sự kiện onClickListener cho từng item trong RecyclerView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopularDomain product = productList.get(holder.getAdapterPosition());
                if (product != null) {
                    Intent intent = new Intent(context, DetailActivity.class);

                    intent.putExtra("category","iPhone");
                    intent.putExtra("category","iPad");
                    intent.putExtra("category","category");
                    intent.putExtra("itemPic", product.getPicUrl());
                    intent.putExtra("titleDetail", product.getTitle());
                    intent.putExtra("priceDetail", product.getPrice());
                    intent.putExtra("ratingDetail", product.getScore());
                    intent.putExtra("reviewDetail", product.getReview());
                    intent.putExtra("descriptionDetail", product.getDecription());
                    intent.putExtra("numberInChartDetail", product.getNumberInChart());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    private void loadDataFromFirebase() {
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                PopularDomain product = dataSnapshot.getValue(PopularDomain.class);
                productList.add(product);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView scrTxt;
        TextView titleTxt;
        TextView feeTxt;
        TextView reviewTxt;
        ImageView pic;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            scrTxt = itemView.findViewById(R.id.scrTxt);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            feeTxt = itemView.findViewById(R.id.feeTxt);
            reviewTxt = itemView.findViewById(R.id.reviewTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
