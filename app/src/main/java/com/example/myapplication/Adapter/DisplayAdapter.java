package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.domain.PopularDomain;

import java.util.List;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.ProductViewHolder> {
    private List<PopularDomain> productList;
    private Context context;

    public DisplayAdapter(List<PopularDomain> productList){
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pup_list, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        PopularDomain product = productList.get(position);
        //hiển thị thông tin sp vào ViewHolder
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView, feeTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pic);
            titleTextView = itemView.findViewById(R.id.titleTxt);
            feeTextView = itemView.findViewById(R.id.feeTxt);
        }

        public void bind(PopularDomain product) {
            // Hiển thị thông tin của sản phẩm
            // Ví dụ: imageView.setImageResource(product.getImageResource());
            // nameTextView.setText(product.getName());
            // priceTextView.setText(product.getPrice());
        }
    }
}

//    Trong đoạn code trên:
//
//        ProductAdapter là lớp Adapter cho RecyclerView.
//        Trong phương thức onCreateViewHolder(), chúng ta inflate layout cho mỗi mục của RecyclerView.
//        Trong phương thức onBindViewHolder(), chúng ta thiết lập dữ liệu cho mỗi ViewHolder bằng cách gọi phương thức bind().
//        Lớp ProductViewHolder đại diện cho ViewHolder của mỗi mục sản phẩm trong RecyclerView.
//        Trong phương thức bind(), bạn sẽ thiết lập dữ liệu cho ViewHolder dựa trên thông tin của sản phẩm.

