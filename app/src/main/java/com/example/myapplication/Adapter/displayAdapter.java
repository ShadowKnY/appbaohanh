package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activity.DetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.domain.PopularDomain;

import java.util.List;

public class displayAdapter extends RecyclerView.Adapter<displayAdapter.ProductViewHolder> {
    private Context context;
    private List<PopularDomain> productList;

    public displayAdapter(List<PopularDomain> productList) {
        this.productList = productList;
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
                // Lấy ID của sản phẩm
                String productId = product.getID();
                // Tạo Intent để chuyển đến DetailActivity
                Intent intent = new Intent(context, DetailActivity.class);
                // Truyền ID của sản phẩm qua Intent
                intent.putExtra("product_id", productId);
                // Khởi chạy DetailActivity
                context.startActivity(intent);
            }
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
