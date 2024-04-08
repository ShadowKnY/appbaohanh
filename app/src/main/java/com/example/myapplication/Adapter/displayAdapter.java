package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.domain.PopularDomain;

import java.util.List;

public class displayAdapter extends RecyclerView.Adapter<displayAdapter.productViewHolder> {
    Context context;
    private List<PopularDomain> mListProduct;

    public displayAdapter(List<PopularDomain> mListProduct) {

        this.mListProduct = mListProduct;
    }

    @NonNull
    @Override
    public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pup_list,parent,false);
        context = parent.getContext();
        return new productViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productViewHolder holder, int position) {
        PopularDomain product = mListProduct.get(position);
        if(product ==null){
            return;
        }
        holder.scrTxt.setText(String.valueOf(product.getScore()));
        holder.titleTxt.setText(product.getTitle());
        holder.feeTxt.setText(String.valueOf(product.getPrice()));
        holder.reviewTxt.setText(String.valueOf(product.getReview()));
        Glide.with(context)
                .load(product.getPicUrl())
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        if(mListProduct != null)
            return mListProduct.size();
        return 0;
    }

    public class productViewHolder extends RecyclerView.ViewHolder{

        TextView scrTxt;
        TextView titleTxt;
        TextView feeTxt;
        TextView reviewTxt;
        ImageView pic;



        public productViewHolder(@NonNull View itemView) {
            super(itemView);
            scrTxt = itemView.findViewById(R.id.scrTxt);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            feeTxt = itemView.findViewById(R.id.feeTxt);
            reviewTxt = itemView.findViewById(R.id.reviewTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
