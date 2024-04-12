package com.example.myapplication.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.domain.PopularDomain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    private DatabaseReference cartRef;

    String userId;
    PopularDomain cartItem;

    private List<PopularDomain> cartItems;
    private TextView subtotalTextView,dlvTxt,taxTxt,totalTxt;// TextView để hiển thị tổng số tiền

    public CartAdapter(List<PopularDomain> cartItems, TextView subtotalTextView, TextView dlvTxt, TextView taxTxt,TextView totalTxt) {
        this.cartItems = cartItems;
        this.subtotalTextView = subtotalTextView;
        this.dlvTxt = dlvTxt;
        this.taxTxt = taxTxt;
        this.totalTxt = totalTxt;
        user = firebaseAuth.getCurrentUser();
        if(user !=null){
            userId = user.getUid();
        }
        cartRef = FirebaseDatabase.getInstance().getReference().child("carts").child(userId);
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
//            cartItem = new PopularDomain();
        cartItem = cartItems.get(position);
        user = firebaseAuth.getCurrentUser();
        if(user !=null){
            userId = user.getUid();
        }
        holder.bind(cartItem); // Chỉ cần chuyển cartItem, không cần chuyển itemId
        holder.minusCartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = cartItem.getQuantity();
                int newQuantity = currentQuantity - 1;
                if (newQuantity <= 0) {
                    // Hiển thị hộp thoại xác nhận xóa sản phẩm khi số lượng giảm về 0
                    holder.showRemoveItemDialog(cartItem.getItemId()); // Chuyển itemId vào hàm
                } else {
                    // Giảm số lượng sản phẩm đi một đơn vị
                    holder.numberItemTxt.setText(String.valueOf(newQuantity));
                    holder.totalEachitem.setText(String.format("%.2f", cartItem.getPrice() * newQuantity));
                    cartItem.setQuantity(newQuantity); // Cập nhật số lượng trong cartItem
                    holder.updateSubtotal(); // Cập nhật tổng số tiền sau khi giảm số lượng sản phẩm
                    holder.updateDelivery();
                    holder.updateTax();
                    holder.updateTotal();
                }
            }
        });

        holder.plusCartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = cartItem.getQuantity();
                int newQuantity = currentQuantity + 1;
                // Tăng số lượng sản phẩm lên một
                holder.numberItemTxt.setText(String.valueOf(newQuantity));
                holder.totalEachitem.setText(String.format("%.2f", cartItem.getPrice() * newQuantity));
                cartItem.setQuantity(newQuantity); // Cập nhật số lượng trong cartItem
                holder.updateSubtotal(); // Cập nhật tổng số tiền sau khi tăng số lượng sản phẩm
                holder.updateDelivery();
                holder.updateTax();
                holder.updateTotal();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTxt;
        private ImageView pic;
        private TextView feeEachitem;
        private TextView totalEachitem;
        private TextView numberItemTxt;
        private TextView plusCartbtn;
        private TextView minusCartbtn;

        public CartViewHolder(View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
            feeEachitem = itemView.findViewById(R.id.feeEachitem);
            numberItemTxt = itemView.findViewById(R.id.numberItemTxt);
            totalEachitem = itemView.findViewById(R.id.totalEachitem);
            numberItemTxt = itemView.findViewById(R.id.numberItemTxt);
            minusCartbtn = itemView.findViewById(R.id.minusCartbtn);
            plusCartbtn = itemView.findViewById(R.id.plusCartbtn);
        }


        //sự kiện cho quantity, tăng giảm số lượng sản phẩm
        public void bind(PopularDomain cartItem) {
            titleTxt.setText(cartItem.getTitle());
            Picasso.get().load(cartItem.getPicUrl()).into(pic);
            String formattedPrice = String.format("%.2f", cartItem.getPrice());
            feeEachitem.setText(formattedPrice);
            int quantity = cartItem.getQuantity();
            numberItemTxt.setText(String.valueOf(quantity));
            totalEachitem.setText(String.format("%.2f", cartItem.getPrice() * quantity));


        }

        private void showRemoveItemDialog(String itemId) {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setMessage("Do you want to remove this item from cart?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (userId != null && cartItem != null && cartItem.getItemId() != null) {
                                DatabaseReference itemRef = FirebaseDatabase.getInstance()
                                        .getReference("carts")
                                        .child(userId)
                                        .child(cartItem.getItemId());
                                itemRef.removeValue();
                                int position = getAbsoluteAdapterPosition();
                                if (position != RecyclerView.NO_POSITION) {
                                    cartItems.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, cartItems.size());
                                } else {
                                    Log.e("CartAdapter", "Invalid adapter position");
                                }
                            } else {
                                Log.e("CartAdapter", "userId or cartItem is null");
                            }
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }

        private void updateSubtotal() {
            double subtotal = 0;
            for (PopularDomain item : cartItems) {
                subtotal += item.getPrice() * item.getQuantity();
            }
            subtotalTextView.setText(String.format("%.2f", subtotal));
//            cartRef.child("subTotal").setValue(subtotal); // Cập nhật subTotal lên Firebase


        }
        private void updateDelivery(){
            double deleTotal = 0;
            String subtotal = subtotalTextView.getText().toString();
            double subttValue = Double.parseDouble(subtotal);
            deleTotal = subttValue * 0.02;
            dlvTxt.setText(String.format("%.2f",deleTotal));
//            cartRef.child("deleTotal").setValue(deleTotal); // Cập nhật subTotal lên Firebase

        }
        private  void updateTax(){
            double tax = 0;
            String subtotal = subtotalTextView.getText().toString();
            double subttValue = Double.parseDouble(subtotal);
            tax = subttValue * 0.08;
            taxTxt.setText(String.format("%.2f",tax));
//            cartRef.child("tax").setValue(tax); // Cập nhật subTotal lên Firebase

        }

        private void updateTotal(){
            double total = 0;
            String subtotal = subtotalTextView.getText().toString();
            double subttValue = Double.parseDouble(subtotal);
            String delv = dlvTxt.getText().toString();
            double delvValue = Double.parseDouble(delv);
            String tax = taxTxt.getText().toString();
            double taxValue = Double.parseDouble(tax);
            total = subttValue + delvValue + taxValue;
            totalTxt.setText(String.format("%.2f",total));
//            cartRef.child("total").setValue(total); // Cập nhật subTotal lên Firebase

        }
    }
}